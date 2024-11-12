package ag.orca.living.core.repo.room;

import ag.orca.living.core.dao.room.LivingRoomInteractMapper;
import ag.orca.living.core.entity.room.LivingRoomInteract;
import ag.orca.living.core.enums.OperateAllMuteEnum;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.ROOM_INTERACT_PREFIX;

@Repository
public class LivingRoomInteractRepo extends AbstractObjCacheRepo<LivingRoomInteract> {
    @Resource
    LivingRoomInteractMapper livingRoomInteractMapper;

    @Override
    protected String getPrefix() {
        return ROOM_INTERACT_PREFIX;
    }

    @Override
    protected LivingRoomInteract getFromDb(Long id) {
        return livingRoomInteractMapper.findLivingRoomInteractByRoomId(id);
    }

    @Override
    protected Class<LivingRoomInteract> clazz() {
        return LivingRoomInteract.class;
    }

    public Optional<LivingRoomInteract> findLivingRoomInteractByRoomId(Long roomId) {
        return Optional.ofNullable(getCache(roomId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoomInteract livingRoomInteract) {
        Long roomId = livingRoomInteract.getRoomId();
        LivingRoomInteract interact = livingRoomInteractMapper.findLivingRoomInteractByRoomId(roomId);
        if (Objects.nonNull(interact)) {
            livingRoomInteract.setId(interact.getId());
            livingRoomInteractMapper.update(livingRoomInteract);
        } else {
            livingRoomInteractMapper.insert(livingRoomInteract);
        }
        // 重新设置缓存
        refreshCache(roomId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void operateAllMuteStatus(Long roomId, OperateAllMuteEnum operate) {
        Optional<LivingRoomInteract> optional = findLivingRoomInteractByRoomId(roomId);
        optional.ifPresent(interact -> {
            interact.setAllMute(operate.getCode());
            interact.setUpdateTime(LocalDateTime.now());
            livingRoomInteractMapper.update(interact);
            stringRedisTemplate.opsForValue().set(buildKey(roomId), JsonUtil.beanToJson(interact));
        });
    }
}
