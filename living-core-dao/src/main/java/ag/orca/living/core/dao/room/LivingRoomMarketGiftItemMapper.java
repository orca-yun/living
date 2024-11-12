package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingRoomMarketGiftItemMapper {

    LivingRoomMarketGiftItem findById(@Param("id") Long id);

    List<LivingRoomMarketGiftItem> findByIds(@Param("ids") List<Long> ids);


    int insert(LivingRoomMarketGiftItem item);


    int update(LivingRoomMarketGiftItem item);


    List<LivingRoomMarketGiftItem> findListByRoomId(@Param("roomId") Long roomId);


    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    int updateStatusById(@Param("id") Long id,
                         @Param("status") Integer status,
                         @Param("dateTime") LocalDateTime dateTime);


    List<LivingRoomMarketGiftItem> findListByRoomIdAndStatus(@Param("roomId") Long roomId,
                                                             @Param("status") Integer status);

    int updatePriorityById(@Param("id") Long id,
                           @Param("priority") Integer priority,
                           @Param("dateTime") LocalDateTime dateTime);

    int countGiftWithUsed(@Param("giftLibIds") List<Long> giftLibIds);

    int logicDelByRoomId(@Param("roomId") Long roomId, @Param("dateTime") LocalDateTime dateTime);
}
