package ag.orca.living.core.repo.room;

import ag.orca.living.core.dao.room.LivingRoomPermissionMapper;
import ag.orca.living.core.entity.room.LivingRoomPermission;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.ROOM_PERMISSION_PREFIX;

@Repository
public class LivingRoomPermissionRepo extends AbstractObjCacheRepo<LivingRoomPermission> {
    @Resource
    LivingRoomPermissionMapper livingRoomPermissionMapper;

    @Override
    protected String getPrefix() {
        return ROOM_PERMISSION_PREFIX;
    }

    @Override
    protected LivingRoomPermission getFromDb(Long id) {
        return livingRoomPermissionMapper.findLivingRoomPermissionByRoomId(id);
    }

    @Override
    protected Class<LivingRoomPermission> clazz() {
        return LivingRoomPermission.class;
    }

    public Optional<LivingRoomPermission> findLivingRoomPermissionByRoomId(Long roomId) {
        return Optional.ofNullable(getCache(roomId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoomPermission livingRoomPermission) {
        Long roomId = livingRoomPermission.getRoomId();
        LivingRoomPermission permission = livingRoomPermissionMapper.findLivingRoomPermissionByRoomId(roomId);
        if (Objects.nonNull(permission)) {
            livingRoomPermission.setId(permission.getId());
            livingRoomPermissionMapper.update(livingRoomPermission);
        } else {
            livingRoomPermissionMapper.insert(livingRoomPermission);
        }
        refreshCache(roomId);
    }


}
