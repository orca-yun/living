package ag.orca.living.core.repo.room;

import ag.orca.living.core.dao.room.LivingRoomMarketGoodsMapper;
import ag.orca.living.core.entity.room.LivingRoomMarketGoods;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.ROOM_MARKET_GOODS_PREFIX;

@Repository
public class LivingRoomMarketGoodsRepo extends AbstractObjCacheRepo<LivingRoomMarketGoods> {
    @Resource
    LivingRoomMarketGoodsMapper livingRoomMarketGoodsMapper;


    @Override
    protected String getPrefix() {
        return ROOM_MARKET_GOODS_PREFIX;
    }

    @Override
    protected LivingRoomMarketGoods getFromDb(Long id) {
        return livingRoomMarketGoodsMapper.findLivingRoomMarketGoodsByRoomId(id);
    }

    @Override
    protected Class<LivingRoomMarketGoods> clazz() {
        return LivingRoomMarketGoods.class;
    }

    public Optional<LivingRoomMarketGoods> findLivingRoomMarketGoodsByRoomId(Long roomId) {
        return Optional.ofNullable(getCache(roomId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoomMarketGoods livingRoomMarketGoods) {
        Long roomId = livingRoomMarketGoods.getRoomId();
        LivingRoomMarketGoods goods = livingRoomMarketGoodsMapper.findLivingRoomMarketGoodsByRoomId(roomId);
        if (Objects.nonNull(goods)) {
            livingRoomMarketGoods.setId(goods.getId());
            livingRoomMarketGoodsMapper.update(livingRoomMarketGoods);
        } else {
            livingRoomMarketGoodsMapper.insert(livingRoomMarketGoods);
        }
        refreshCache(roomId);

    }


}
