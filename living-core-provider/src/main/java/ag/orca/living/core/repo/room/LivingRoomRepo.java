package ag.orca.living.core.repo.room;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.dao.room.LivingRoomMapper;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.enums.LivingTypeEnum;
import ag.orca.living.core.enums.VideoTypeEnum;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.*;

@Repository
public class LivingRoomRepo extends AbstractObjCacheRepo<LivingRoom> {


    @Resource
    LivingRoomMapper livingRoomMapper;

    @Resource
    LivingRoomMarketGoodsItemRepo livingRoomMarketGoodsItemRepo;

    @Resource
    LivingRoomMarketGiftItemRepo livingRoomMarketGiftItemRepo;

    @Override
    protected String getPrefix() {
        return ROOM_PREFIX;
    }

    @Override
    protected LivingRoom getFromDb(Long id) {
        return livingRoomMapper.findLivingRoomById(id);
    }

    @Override
    protected Class<LivingRoom> clazz() {
        return LivingRoom.class;
    }

    public Optional<LivingRoom> findLivingRoomById(Long id) {
        return Optional.ofNullable(getCache(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoom entity) {
        Optional<LivingRoom> optional = findSameNameRoom(entity.getOrgId(), entity.getName());
        OrcaAssert.match(optional.isPresent(), I18nUtil.getMessage("name.repeat"));
        livingRoomMapper.insert(entity);
        refreshCache(entity.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(LivingRoom entity) {

        Optional<LivingRoom> optional = findSameNameRoom(entity.getOrgId(), entity.getName());
        optional.ifPresent(s -> OrcaAssert.match(!Objects.equals(s.getId(), entity.getId()), I18nUtil.getMessage("name.repeat")));

        LivingRoom old = getFromDb(entity.getId());
        VideoTypeEnum videoType = VideoTypeEnum.ofCode(old.getVideoType());
        OrcaAssert.match(videoType != VideoTypeEnum.ofCode(entity.getVideoType()), I18nUtil.getMessage("video.type.not.change"));
        LivingTypeEnum livingType = LivingTypeEnum.ofCode(old.getLivingType());
        OrcaAssert.match(livingType != LivingTypeEnum.ofCode(entity.getLivingType()), I18nUtil.getMessage("room.type.not.change"));
        if (VideoTypeEnum.VIDEO == videoType) {
            OrcaAssert.match(!Objects.equals(old.getMediaBizId(), entity.getMediaBizId()), I18nUtil.getMessage("video.media.not.change"));
        }
        livingRoomMapper.update(entity);
        refreshCache(entity.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        List<LivingRoom> rooms = livingRoomMapper.findListByIdList(ids);
        int cnt = CommonConvert.filter(rooms, room -> room.getStatus() == LiveRecordStatusEnum.living.getCode()).size();
        OrcaAssert.match(cnt > 0, I18nUtil.getMessage("room.is.living"));

        livingRoomMapper.logicDel(orgId, ids, LocalDateTime.now());
        // 清理ROOM缓存，设置失效时间即可
        ids.forEach(id -> {
            // room 直接清理掉
            stringRedisTemplate.delete(buildKey(id));
            stringRedisTemplate.delete(ROOM_PERMISSION_PREFIX + id);
            stringRedisTemplate.delete(ROOM_PAGE_PREFIX + id);
            stringRedisTemplate.delete(ROOM_INTERACT_PREFIX + id);
            stringRedisTemplate.delete(ROOM_MARKET_GOODS_PREFIX + id);

            livingRoomMarketGoodsItemRepo.logicDelByRoomId(orgId, id);
            livingRoomMarketGiftItemRepo.logicDelByRoomId(orgId, id);

        });

    }


    public List<LivingRoom> findListByOrgId(Long orgId) {
        return livingRoomMapper.findListByOrgId(orgId);
    }


    public List<LivingRoom> findListByOrgIdAndNameLikeAndVideoType(Long orgId, String name, Integer videoType) {
        return livingRoomMapper.findListByOrgIdAndNameLikeAndVideoType(orgId, name, videoType);
    }

    public List<LivingRoom> findListByIdList(List<Long> roomIds) {
        return livingRoomMapper.findListByIdList(roomIds);
    }

    public Optional<LivingRoom> findSameNameRoom(Long orgId, String name) {
        return Optional.ofNullable(livingRoomMapper.findSameNameRoom(orgId, name));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modRoomPwd(Long roomId, String password, CrossRoleEnum role) {
        LivingRoom entity = getFromDb(roomId);
        entity.setAnchorPwd(role == CrossRoleEnum.ANCHOR ? password : entity.getAnchorPwd());
        entity.setAssistantPwd(role == CrossRoleEnum.ASSISTANT ? password : entity.getAssistantPwd());
        OrcaAssert.match(StringUtils.equalsIgnoreCase(entity.getAnchorPwd(), entity.getAssistantPwd()),
                I18nUtil.getMessage("anchor.assistant.pwd.same"));
        livingRoomMapper.update(entity);
        refreshCache(entity.getId());

    }

    public int existsRoomId(Long roomId) {
        return livingRoomMapper.existsId(roomId);
    }
}
