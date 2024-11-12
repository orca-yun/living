package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingRoomMarketGoodsItemMapper {

    LivingRoomMarketGoodsItem findById(@Param("id") Long id);


    int insert(LivingRoomMarketGoodsItem livingRoomMarketGoodsItem);


    int update(LivingRoomMarketGoodsItem livingRoomMarketGoodsItem);


    List<LivingRoomMarketGoodsItem> findListByRoomId(@Param("roomId") Long roomId);


    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    int updateSellStatusById(@Param("id") Long id,
                             @Param("sellStatus") Integer sellStatus,
                             @Param("dateTime") LocalDateTime dateTime);


    List<LivingRoomMarketGoodsItem> findListByRoomIdAndSellStatus(@Param("roomId") Long roomId,
                                                                  @Param("sellStatusList") List<Integer> sellStatusList);

    int updatePriorityById(@Param("id") Long id,
                           @Param("priority") Integer priority,
                           @Param("dateTime") LocalDateTime dateTime);

    List<LivingRoomMarketGoodsItem> findListByIds(@Param("ids") List<Long> ids);

    int countGoodsWithUsed(@Param("goodsLibIds") List<Long> goodsLibIds);

    int logicDelByRoomId(@Param("roomId") Long roomId,
                         @Param("dateTime") LocalDateTime dateTime);

    int batchUpdateSellStatus(@Param("ids") List<Long> ids,
                              @Param("sellStatus") Integer sellStatus,
                              @Param("dateTime") LocalDateTime dateTime);
}
