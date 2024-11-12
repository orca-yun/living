package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomMarketGoodsRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsVo;

import java.util.Optional;

public interface LivingRoomMarketGoodsService {

    Optional<LivingRoomMarketGoodsVo> findByRoomId(Long roomId);

    void save(LivingRoomMarketGoodsRo ro);


}
