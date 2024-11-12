package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomMarketGiftItemRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;

import java.util.List;
import java.util.Optional;

public interface LivingRoomMarketGiftItemService {

    List<LivingRoomMarketGiftItemVo> findListByRoomIdForShare(Long roomId);

    List<LivingRoomMarketGiftItemVo> findListByRoomId(Long roomId);

    Optional<LivingRoomMarketGiftItemVo> findById(Long id);


    void batchSave(Long orgId, Long roomId, LivingRoomMarketGiftItemRo ro);


}
