package ag.orca.living.core.repo.order;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.dao.order.OrderInfoMapper;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.thirdparty.tlpay.TlPayNotifyResult;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class OrderInfoRepo {

    @Resource
    OrderInfoMapper orderInfoMapper;


    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderInfo orderInfo, LivingRoomMarketGoodsItem goodsItem, SysTlpay sysTlpay) {
        // 判断是否需要限制单
        BoolEnum bool = BoolEnum.ofCode(goodsItem.getBounds());
        if (BoolEnum.TRUE == bool) {
            List<Integer> orderStatus = new ArrayList<>();
            orderStatus.add(OrderStatusEnum.PAYED.getCode());
            orderStatus.add(OrderStatusEnum.INIT.getCode());
            List<OrderInfo> orderInfos = orderInfoMapper.findLimitationOrderList(
                    orderInfo.getRoomId(),
                    orderInfo.getUserId(),
                    orderInfo.getGoodsId(),
                    orderStatus,
                    orderInfo.getOrderDate());
            long cnt = CommonConvert.filter(orderInfos, s -> s.getOrderStatus() == OrderStatusEnum.INIT.getCode()).size();
            OrcaAssert.match(cnt > 0, I18nUtil.getMessage("goods.bounds.overload.wait"));
            cnt = CommonConvert.filter(orderInfos, s -> s.getOrderStatus() == OrderStatusEnum.PAYED.getCode()).size();
            OrcaAssert.match(cnt > 0, MessageFormat.format(I18nUtil.getMessage("goods.bounds.overload"), goodsItem.getName()));

        }
        orderInfoMapper.insert(orderInfo);
    }

    public OrderInfo findLatestOrderByUserId(Long userId) {
        return orderInfoMapper.findLatestOrderByUserId(userId);
    }

    public OrderInfo findOrderInfoById(Long id) {
        return orderInfoMapper.findOrderInfoById(id);
    }


    public List<OrderInfo> findListByOrgIdAndCondition(Long orgId,
                                                       Long roomId,
                                                       List<Long> channelIds,
                                                       Integer orderStatus,
                                                       Long userId,
                                                       LocalDate orderDate) {
        return orderInfoMapper.findListByOrgIdAndCondition(orgId, roomId, channelIds, orderStatus, userId, orderDate);
    }


    //订单支付成功
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(OrderInfo orderInfo, TlPayNotifyResult payResult) {
        OrderInfo doubleOrderInfo = findOrderInfoById(orderInfo.getId());
        OrderStatusEnum status = OrderStatusEnum.ofCode(doubleOrderInfo.getOrderStatus());
        OrcaAssert.match(status != OrderStatusEnum.INIT, I18nUtil.getMessage("order.status.change.err"));
        OrderInfo updateOrder = OrderInfoConvert.payToOrderBuild(doubleOrderInfo.getId(), payResult, doubleOrderInfo);
        if (Objects.nonNull(updateOrder)) {
            //查询订单的分佣比例和支付手续费
            orderInfoMapper.update(updateOrder);
        }

    }

    //撤销订单
    @Transactional(rollbackFor = Exception.class)
    public void refundPay(Long id) {
        OrderInfo updateOrder = OrderInfoConvert.refundPay(id);
        orderInfoMapper.update(updateOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        OrderInfo doubleOrderInfo = findOrderInfoById(orderId);
        OrderStatusEnum status = OrderStatusEnum.ofCode(doubleOrderInfo.getOrderStatus());
        OrcaAssert.match(status != OrderStatusEnum.INIT, I18nUtil.getMessage("order.status.change.err"));
        OrderInfo updateOrder = OrderInfoConvert.cancelPay(orderId);
        orderInfoMapper.update(updateOrder);
    }


    public List<OrderInfo> findUserOrderListByRoomIdAndUidAndOrderDate(Long orgId,
                                                                       Long roomId,
                                                                       Long userId,
                                                                       LocalDate orderDate,
                                                                       List<Integer> orderStatus) {
        return orderInfoMapper.findUserOrderListByRoomIdAndUidAndOrderDate(orgId, roomId, userId, orderDate, orderStatus);
    }


}
