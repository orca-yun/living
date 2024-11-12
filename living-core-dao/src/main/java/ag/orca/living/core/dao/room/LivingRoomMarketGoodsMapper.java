package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomMarketGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LivingRoomMarketGoodsMapper {
    LivingRoomMarketGoods findLivingRoomMarketGoodsByRoomId(@Param("roomId") Long roomId);

    int insert(LivingRoomMarketGoods livingRoomMarketGoods);

    int update(LivingRoomMarketGoods livingRoomMarketGoods);


}
