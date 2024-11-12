package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomPermissionRo;
import ag.orca.living.core.vo.room.LivingRoomPermissionVo;

import java.util.Optional;

public interface LivingRoomPermissionService {
    Optional<LivingRoomPermissionVo> findByRoomId(Long roomId);

    void save(LivingRoomPermissionRo ro);
}
