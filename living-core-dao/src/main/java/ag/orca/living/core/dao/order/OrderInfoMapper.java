package ag.orca.living.core.dao.order;

import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.vo.stats.ChannelAmountStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomFinanceStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomOrderCountStaticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int insert(OrderInfo orderInfo);

    int update(OrderInfo orderInfo);

    OrderInfo findLatestOrderByUserId(@Param("userId") Long userId);

    OrderInfo findOrderInfoById(@Param("id") Long id);

    List<OrderInfo> findLimitationOrderList(@Param("roomId") Long roomId,
                                            @Param("userId") Long userId,
                                            @Param("goodsId") Long goodsId,
                                            @Param("orderStatus") List<Integer> orderStatus,
                                            @Param("orderDate") LocalDate orderDate);


    List<OrderInfo> findListByOrgIdAndCondition(@Param("orgId") Long orgId,
                                                @Param("roomId") Long roomId,
                                                @Param("channelIds") List<Long> channelIds,
                                                @Param("orderStatus") Integer orderStatus,
                                                @Param("userId") Long userId,
                                                @Param("orderDate") LocalDate orderDate);

    LivingRoomOrderCountStaticsVo roomOrderCountStatics(@Param("roomId") Long roomId,
                                                        @Param("start") LocalDate start,
                                                        @Param("stop") LocalDate stop);


    LivingRoomFinanceStaticsVo roomOrderFinanceStatics(@Param("roomId") Long roomId,
                                                       @Param("start") LocalDate start,
                                                       @Param("stop") LocalDate stop);


    List<ChannelAmountStaticsVo> findChannelStaticsByOrgIdAndOrderDateAndOrderStatus(
            @Param("orgId") Long orgId,
            @Param("orderDate") LocalDate orderDate,
            @Param("orderStatus") Integer orderStatus);


    List<OrderInfo> findUserOrderListByRoomIdAndUidAndOrderDate(@Param("orgId") Long orgId,
                                                                @Param("roomId") Long roomId,
                                                                @Param("userId") Long userId,
                                                                @Param("orderDate") LocalDate orderDate,
                                                                @Param("orderStatus") List<Integer> orderStatus);
}
