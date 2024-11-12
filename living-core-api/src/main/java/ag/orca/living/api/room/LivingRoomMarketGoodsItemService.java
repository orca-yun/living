package ag.orca.living.api.room;

import ag.orca.living.core.ro.room.LivingRoomMarketGoodsItemRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;

import java.util.List;
import java.util.Optional;

public interface LivingRoomMarketGoodsItemService {

    List<LivingRoomMarketGoodsItemVo> findListByRoomIdForShare(Long roomId);

    List<LivingRoomMarketGoodsItemVo> findListByRoomId(Long roomId);

    Optional<LivingRoomMarketGoodsItemVo> findById(Long id);

    void batchSave(Long orgId, Long roomId, LivingRoomMarketGoodsItemRo ros);

    List<LivingRoomMarketGoodsItemVo> findListByIdList(List<Long> ids);
}
