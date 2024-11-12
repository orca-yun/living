package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomConvert;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.repo.live.LivingRecordRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.room.manage.LivingRoomManage;
import ag.orca.living.core.ro.query.QueryRoomRo;
import ag.orca.living.core.ro.room.LivingRoomPwdRo;
import ag.orca.living.core.ro.room.LivingRoomRo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static ag.orca.living.common.OrcaConst.SEQ_LEN_8;


@Slf4j
@DubboService
public class LivingRoomProvider implements LivingRoomService {

    private static final int RANDOM_LEN = 15;

    private static final int BATCH_PWD = 3;

    @Resource
    LivingRoomRepo livingRoomRepo;

    @Resource
    LivingRoomManage livingRoomManage;

    @DubboReference
    LivingSeqService livingSeqService;

    @Resource
    LivingRecordRepo livingRecordRepo;

    @Override
    public void modRoomPwd(Long roomId, LivingRoomPwdRo ro) {
        CrossRoleEnum role = CrossRoleEnum.ofCode(ro.getRole());
        livingRoomRepo.modRoomPwd(roomId, ro.getPassword(), role);
    }


    @Override
    public Pair<List<LivingRoomVo>, Long> findPageList(Long orgId, QueryRoomRo ro) {
        //判断直播过滤状态有没有
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<LivingRoom> rooms = livingRoomRepo.findListByOrgIdAndNameLikeAndVideoType(orgId, ro.getName(), ro.getVideoType());
        PageInfo<LivingRoom> pageInfo = new PageInfo<>(rooms);
        List<LivingRoomVo> vos = CommonConvert.map(pageInfo.getList(), LivingRoomConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<LivingRoomVo> findList(Long orgId) {
        List<LivingRoom> rooms = livingRoomRepo.findListByOrgId(orgId);
        return CommonConvert.map(rooms, LivingRoomConvert::entityToVo);
    }


    private void randomPwd(LivingRoom entity) {
        List<String> randomArr = null;
        do {
            randomArr = livingSeqService.batchRandomStr(BATCH_PWD, RANDOM_LEN);
            String anchorPwd = randomArr.get(0);
            String assistPwd = randomArr.get(1);
            if (!StringUtils.equalsIgnoreCase(anchorPwd, assistPwd)) {
                break;
            }
        } while (true);
        if (Objects.nonNull(entity)) {
            entity.setAnchorPwd(randomArr.get(0));
            entity.setAssistantPwd(randomArr.get(1));
            entity.setSharePwd(randomArr.get(2));
        }
    }

    public Long nextRoomId() {
        Long roomId = livingSeqService.fixLenNextId(SEQ_LEN_8);
        return livingRoomRepo.existsRoomId(roomId) <= 0 ? roomId : nextRoomId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoomRo ro) {
        LivingRoom entity = LivingRoomConvert.roToEntity(ro);
        randomPwd(entity);
        if (Objects.nonNull(entity)) {
            Long roomId = nextRoomId();
            entity.setId(roomId);
            entity.setStatus(LiveRecordStatusEnum.lived.getCode());
            livingRoomManage.saveAndBuildLivingRoom(entity);
        }
    }

    private void nullPwd(LivingRoom room) {
        if (Objects.nonNull(room)) {
            room.setAnchorPwd(null);
            room.setAssistantPwd(null);
            room.setSharePwd(null);
        }
    }

    @Override
    public void edit(LivingRoomRo ro) {
        LivingRoom entity = LivingRoomConvert.roToEntity(ro);
        if (Objects.nonNull(entity)) {
            nullPwd(entity);
            livingRoomRepo.update(entity);
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            livingRoomRepo.remove(orgId, ids);
        }
    }


    public Optional<LivingRoomVo> findById(Long id) {
        return livingRoomRepo.findLivingRoomById(id).map(LivingRoomConvert::entityToVo);
    }

    @Override
    public List<LivingRoomVo> findLatestTopLivingRoom(Long orgId, LocalDate date) {
        List<LivingRoomLiveRecord> records = livingRecordRepo.findLivingRecordsByOrgIdAndStartDate(orgId, date);
        if (CollectionUtils.isNotEmpty(records)) {
            List<Long> roomIds = CommonConvert.distinctMap(records, LivingRoomLiveRecord::getRoomId);
            return CommonConvert.map(livingRoomRepo.findListByIdList(roomIds), LivingRoomConvert::entityToVo);
        }
        return Collections.emptyList();
    }

    @Override
    public List<LivingRoomVo> findListByIdList(List<Long> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)) {
            return new ArrayList<>();
        }
        List<LivingRoom> rooms = livingRoomRepo.findListByIdList(roomIds);
        return CommonConvert.map(rooms, LivingRoomConvert::entityToVo);
    }


}
