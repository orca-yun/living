package ag.orca.living.provider.media;

import ag.orca.living.api.media.LivingMediaLibService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingMediaLibConvert;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.media.LivingMediaLibRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.ro.media.LivingMediaLibEditRo;
import ag.orca.living.core.ro.query.QueryMediaLibRo;
import ag.orca.living.core.vo.media.LivingMediaLibInfoVo;
import ag.orca.living.core.vo.media.LivingMediaLibVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class LivingMediaLiveProvider implements LivingMediaLibService {


    @Resource
    LivingMediaLibRepo mediaLibRepo;

    @Resource
    LivingRoomRepo roomRepo;

    @Resource
    LivingLiveRepo liveRepo;


    @Override
    public Pair<List<LivingMediaLibInfoVo>, Long> findPageList(Long orgId, QueryMediaLibRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<LivingMediaLib> mediaLibs = mediaLibRepo.findListByOrgIdAndNameLike(orgId, ro.getName());
        PageInfo<LivingMediaLib> pageInfo = new PageInfo<>(mediaLibs);
        List<LivingRoom> rooms = Lists.newArrayList();
        List<LivingRoomLiveRecord> records = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            List<Long> roomIds = CommonConvert.filter(CommonConvert.map(pageInfo.getList(), LivingMediaLib::getRoomId),
                    Objects::nonNull).stream().distinct().toList();
            List<Long> roomRecordIds = CommonConvert.filter(CommonConvert.map(pageInfo.getList(), LivingMediaLib::getRoomRecordId),
                    Objects::nonNull).stream().distinct().toList();
            if (CollectionUtils.isNotEmpty(roomIds)) {
                rooms.addAll(roomRepo.findListByIdList(roomIds));
            }
            if (CollectionUtils.isNotEmpty(roomRecordIds)) {
                records.addAll(liveRepo.findListByRecordIdList(roomRecordIds));
            }
        }
        // 查询所有
        List<LivingMediaLibInfoVo> vos = CommonConvert.map(pageInfo.getList(), s -> LivingMediaLibConvert.entityToVo(s, rooms, records));
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            mediaLibRepo.remove(orgId, ids);
        }
    }

    @Override
    public void saveByManual(Long orgId, String name, String fileName, String bucket, String key) {
        LivingMediaLib lib = LivingMediaLibConvert.buildEntity(orgId, name, fileName, bucket, key);
        mediaLibRepo.save(lib);
    }

    @Override
    public Optional<LivingMediaLibVo> findFirstById(Long id) {
        Optional<LivingMediaLib> optional = mediaLibRepo.findMediaById(id);
        return optional.map(LivingMediaLibConvert::entityToSimpleVo);
    }

    @Override
    public void editMediaLibName(Long orgId, LivingMediaLibEditRo ro) {
        mediaLibRepo.editMediaLibName(orgId, ro.getId(), ro.getName());

    }
}
