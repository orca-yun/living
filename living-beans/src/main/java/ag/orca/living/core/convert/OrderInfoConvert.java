package ag.orca.living.core.convert;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.GoodTypeEnum;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.ro.order.OrderInfoRo;
import ag.orca.living.core.thirdparty.tlpay.TlPayNotifyResult;
import ag.orca.living.core.thirdparty.tlpay.TlQueryResult;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.order.OrderInfoExportVo;
import ag.orca.living.core.vo.order.OrderInfoExtVo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.core.vo.share.LivingShareUserAddressVo;
import ag.orca.living.core.vo.tlpay.TlUnifiedPayInfoVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.OrcaConst.FULL_TIME_FORMATTER;
import static ag.orca.living.common.OrcaConst._100;
import static ag.orca.living.util.NumberUtil.toDouble;

public class OrderInfoConvert {


    public static OrderInfo roToEntity(OrderInfoRo ro,
                                       Long orderId,
                                       String orderNo,
                                       Long userId,
                                       AuthInfo authInfo,
                                       ChannelInfo channelInfo,
                                       LivingRoomMarketGoodsItem goodsItem,
                                       SysTlpay sysTlpay) {
        Long price = goodsItem.getPrice();
        Integer ratio = channelInfo.getCommissionRatio();

        Long subCommission = BigDecimal.valueOf(ratio).multiply(BigDecimal.valueOf(price))
                .divide(_100, 2, RoundingMode.HALF_UP).longValue();
        Integer payFee = channelInfo.getPaymentFee();
        Long actual = BigDecimal.valueOf(subCommission).multiply(_100.subtract(BigDecimal.valueOf(payFee))
                .divide(_100, 2, RoundingMode.HALF_UP)).longValue();

        return Objects.isNull(ro) ? null : OrderInfo.builder()
                .id(orderId)
                .orderNo(orderNo)

                .orderDate(LocalDate.now())
                .orderTime(LocalDateTime.now())
                .timeoutTime(LocalDateTime.now().plusMinutes(sysTlpay.getValid()))

                .commissionRatio(ratio)
                .paymentFee(channelInfo.getPaymentFee())
                .orgId(channelInfo.getOrgId())
                .price(goodsItem.getPrice())
                .realAmt(goodsItem.getPrice())
                .originalPrice(goodsItem.getOriginalPrice())
                .type(goodsItem.getGoodType())
                .goodsId(goodsItem.getId())
                .subCommission(subCommission)
                .actualAccountingAmount(actual)
                .roomId(authInfo.getRoomId())
                .channelId(authInfo.getChannelId())
                .userId(userId)
                .userName(authInfo.getNickname())
                .coupon(ro.getCoupon())
                .recipientName(ro.getRecipientName())
                .recipientPhone(ro.getRecipientPhone())
                .recipientProvince(ro.getRecipientProvince())
                .recipientCity(ro.getRecipientCity())
                .recipientCountry(ro.getRecipientCountry())
                .recipientAddress(ro.getRecipientAddress())

                .tlPayId(sysTlpay.getId())
                .build();
    }

    public static OrderInfo payToOrderBuild(Long id, TlPayNotifyResult payResult, OrderInfo orderInfo) {
        if (Objects.nonNull(payResult)) {
            Integer ratio = orderInfo.getCommissionRatio();
            Long price = payResult.getTrxamt();
            Long subCommission = BigDecimal.valueOf(ratio).multiply(BigDecimal.valueOf(price))
                    .divide(_100, 2, RoundingMode.HALF_UP).longValue();
            Integer payFee = orderInfo.getPaymentFee();
            Long actual = BigDecimal.valueOf(subCommission).multiply(_100.subtract(BigDecimal.valueOf(payFee))
                    .divide(_100, 2, RoundingMode.HALF_UP)).longValue();
            return OrderInfo.builder()
                    .id(id)
                    .transactionId(payResult.getTrxid())
                    .tradeTime(LocalDateTime.parse(payResult.getPaytime(), FULL_TIME_FORMATTER))
                    .realAmt(payResult.getTrxamt())
                    .subCommission(subCommission)
                    .actualAccountingAmount(actual)
                    .orderStatus(OrderStatusEnum.PAYED.getCode())
                    .createTime(orderInfo.getCreateTime())
                    .build();
        }
        return null;


    }


    public static OrderInfo queryToOrderBuild(Long id, TlQueryResult payResult, OrderStatusEnum orderStatus) {
        return OrderInfo.builder()
                .id(id)
                .transactionId(payResult.getTrxid())
                .realAmt(payResult.getTrxamt())
                .orderStatus(orderStatus.getCode())
                .createTime(null)
                .build();
    }


    public static OrderInfo refundPay(Long id) {
        return OrderInfo.builder()
                .id(id)
                .refundTime(LocalDateTime.now())
                .orderStatus(OrderStatusEnum.REFUND.getCode())
                .createTime(null)
                .build();
    }

    public static OrderInfo cancelPay(Long id) {
        return OrderInfo.builder()
                .id(id)
                .cancelTime(LocalDateTime.now())
                .orderStatus(OrderStatusEnum.CANCEL.getCode())
                .createTime(null)
                .build();
    }

    public static LivingShareUserAddressVo entityToShareAddressVo(OrderInfo entity) {
        return Objects.isNull(entity) ? null : LivingShareUserAddressVo.builder()
                .recipientName(entity.getRecipientName())
                .recipientPhone(entity.getRecipientPhone())
                .recipientProvince(entity.getRecipientProvince())
                .recipientCity(entity.getRecipientCity())
                .recipientCountry(entity.getRecipientCountry())
                .recipientAddress(entity.getRecipientAddress())
                .build();
    }

    public static OrderInfoVo entityToVo(OrderInfo entity) {
        return Objects.isNull(entity) ? null : OrderInfoVo.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .transactionId(entity.getTransactionId())
                .roomId(entity.getRoomId())
                .channelId(entity.getChannelId())
                .goodsId(entity.getGoodsId())
                .userId(entity.getUserId())
                .userName(entity.getUserName())

                .tradeTime(entity.getTradeTime())
                .refundTime(entity.getRefundTime())
                .orderDate(entity.getOrderDate())
                .timeoutTime(entity.getTimeoutTime())
                .orderTime(entity.getOrderTime())
                .cancelTime(entity.getCancelTime())

                .type(entity.getType())
                .originalPrice(entity.getOriginalPrice())
                .coupon(entity.getCoupon())
                .price(entity.getPrice())
                .realAmt(entity.getRealAmt())
                .commissionRatio(entity.getCommissionRatio())
                .paymentFee(entity.getPaymentFee())
                .subCommission(entity.getSubCommission())
                .actualAccountingAmount(entity.getActualAccountingAmount())
                .orderStatus(entity.getOrderStatus())
                .recipientName(entity.getRecipientName())
                .recipientPhone(entity.getRecipientPhone())
                .recipientAddress(entity.getRecipientAddress())

                .build();
    }

    public static OrderInfoExtVo voToOrderExtVo(OrderInfoVo o,
                                                Optional<LivingRoomVo> roomVoOptional,
                                                Optional<LivingRoomMarketGoodsItemVo> itemOptional,
                                                Optional<ChannelInfoVo> channelOptional) {

        return OrderInfoExtVo.builder()
                .id(o.getId())
                .orderNo(o.getOrderNo())
                .transactionId(o.getTransactionId())
                .roomId(o.getRoomId())
                .channelId(o.getChannelId())
                .goodsId(o.getGoodsId())
                .userId(o.getUserId())
                .userName(o.getUserName())

                .tradeTime(o.getTradeTime())
                .refundTime(o.getRefundTime())
                .cancelTime(o.getCancelTime())
                .orderDate(o.getOrderDate())
                .orderTime(o.getOrderTime())
                .timeoutTime(o.getTimeoutTime())

                .type(o.getType())
                .originalPrice(o.getOriginalPrice())
                .coupon(o.getCoupon())
                .price(o.getPrice())
                .realAmt(o.getRealAmt())
                .commissionRatio(o.getCommissionRatio())
                .paymentFee(o.getPaymentFee())
                .subCommission(o.getSubCommission())
                .actualAccountingAmount(o.getActualAccountingAmount())
                .orderStatus(o.getOrderStatus())
                .recipientName(o.getRecipientName())
                .recipientPhone(o.getRecipientPhone())
                .recipientAddress(o.getRecipientAddress())

                .goodsImage(itemOptional.map(LivingRoomMarketGoodsItemVo::getImg).orElse(""))
                .goodsName(itemOptional.map(LivingRoomMarketGoodsItemVo::getName).orElse(""))
                .goodsBounds(itemOptional.map(LivingRoomMarketGoodsItemVo::getBounds).orElse(BoolEnum.FALSE.getCode()))
                .channelName(channelOptional.map(ChannelInfoVo::getChannelName).orElse(""))
                .roomName(roomVoOptional.map(LivingRoomVo::getName).orElse(""))
                .build();

    }


    public static OrderInfoExportVo extVoToExportVo(OrderInfoExtVo vo) {
        return OrderInfoExportVo.builder()
                .orderDate(vo.getOrderDate())
                .orderNo(vo.getOrderNo())
                .channelName(vo.getChannelName())
                .roomName(vo.getRoomName())
                .goodsName(vo.getGoodsName())
                .userName(vo.getUserName())
                .orderTime(vo.getOrderTime())
                .tradeTime(vo.getTradeTime())
                .originalPrice(toDouble(vo.getOriginalPrice()))
                .price(toDouble(vo.getPrice()))
                .realAmt(toDouble(vo.getRealAmt()))
                .recipientName(vo.getRecipientName())
                .recipientPhone(vo.getRecipientPhone())
                .goodsTypeName(GoodTypeEnum.ofCode(vo.getType()).getDescribe())
                .orderStatus(OrderStatusEnum.ofCode(vo.getOrderStatus()).getDescribe())
                .goodsBoundsName(BoolEnum.ofCode(vo.getGoodsBounds()).getDescribe())
                .build();
    }


    public static TlUnifiedPayInfoVo buildUnifiedPayInfo(Long orderId, BigDecimal payTotal, Integer payType, String payInfo) {
        return TlUnifiedPayInfoVo.builder()
                .orderNo(orderId.toString())
                .payTotal(payTotal)
                .payType(payType)
                .payInfo(payInfo)
                .build();
    }

}
