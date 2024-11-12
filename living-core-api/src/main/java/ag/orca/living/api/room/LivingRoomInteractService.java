package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomInteractRo;
import ag.orca.living.core.vo.room.LivingRoomInteractVo;

import java.util.Optional;

public interface LivingRoomInteractService {
    Optional<LivingRoomInteractVo> findByRoomId(Long roomId);

    void save(LivingRoomInteractRo ro);

}
