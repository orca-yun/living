package ag.orca.living.core.convert;

import ag.orca.living.core.entity.channel.DayChannelData;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import ag.orca.living.core.vo.order.ChannelLivingOrderVo;

import java.util.Objects;

public class ChannelDataConvert {

    public static ChannelDataDetailVo entityToVo(DayChannelData ro) {
        return Objects.isNull(ro) ? null : ChannelDataDetailVo.builder()
                .id(ro.getId())
                .channelName(ro.getChannelName())
                .channelId(ro.getChannelId())
                .flowPeople(ro.getFlowPeople())
                .transactionAmount(ro.getTransactionAmount())
                .subCommission(ro.getSubCommission())
                .commissionRatio(ro.getCommissionRatio())
                .day(ro.getDay())
                .build();
    }

    public static ChannelLivingOrderVo entityToLivingOrder(OrderInfo ro) {
        return Objects.isNull(ro) ? null : ChannelLivingOrderVo.builder()
                .id(ro.getUserId())
                .orderId(ro.getId())
                .tradeTime(ro.getTradeTime())
                .goodsId(ro.getGoodsId())
                .originalPrice(ro.getOriginalPrice())
                .price(ro.getPrice())
                .coupon(ro.getCoupon())
                .recipientPhone(ro.getRecipientPhone())
                .recipientName(ro.getRecipientName())
                .recipientAddress(ro.getRecipientAddress() == null ? "" : ro.getRecipientProvince() + ro.getRecipientCity() + ro.getRecipientCountry() + ro.getRecipientAddress())
                .build();
    }

}
