package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomPageRo;
import ag.orca.living.core.vo.room.LivingRoomPageVo;

import java.util.Optional;

public interface LivingRoomPageService {
    Optional<LivingRoomPageVo> findByRoomId(Long roomId);

    void save(LivingRoomPageRo ro);
}
