package ag.orca.living.core.entity.order;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户订单信息表
 *
 * @TableName t_day_channel_data
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderInfo extends BaseEntity {
    /**
     * 订单流水号
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * orgId
     */
    private Long orgId;
    /**
     * 支付流水号
     */
    private String transactionId;

    /**
     * 订单发生的直播间id
     */
    private Long roomId;


    /**
     * 订单来源渠道id，目前是6位数字
     */
    private Long channelId;
    /**
     * 交易商品id
     */
    private Long goodsId;
    /**
     * 下单人ID
     */
    private Long userId;
    /**
     * 下单人名称
     */
    private String userName;

    /**
     * 订单超时时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeoutTime;


    /**
     * 订单创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderTime;

    /**
     * 下单时间（支付时间）
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime tradeTime;
    /**
     * 退款时间（支付时间）
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime refundTime;

    /**
     * 取消订单时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime cancelTime;

    /**
     * 商品类型 1 虚拟商品 2 实物商品
     */
    private Integer type;
    /**
     * 商品原价(分)
     */
    private Long originalPrice;
    /**
     * 使用的优惠券
     */
    private String coupon;
    /**
     * 实际付款的金额(分)
     */
    private Long price;

    /**
     * 实际付款的金额(分)
     */
    private Long realAmt;
    /**
     * 当前渠道的分佣比例
     */
    private Integer commissionRatio;
    /**
     * 当前渠道的支付手续费
     */
    private Integer paymentFee;
    /**
     * 订单分佣金额
     */
    private Long subCommission;
    /**
     * 实际分账金额(subCommission * (1-paymentFee)/100)
     */
    private Long actualAccountingAmount;
    /**
     * 订单状态 1待支付 2已支付 3已取消 4 超时未支付
     */
    private Integer orderStatus;
    /**
     * 收件人姓名，type=2必填
     */
    private String recipientName;
    /**
     * 收件人手机号，type=2必填
     */
    private String recipientPhone;
    /**
     * 收件人所在省份，type=2必填
     */
    private String recipientProvince;
    /**
     * 收件人所在城市，type=2必填
     */
    private String recipientCity;
    /**
     * 收件人所在县区，type=2必填
     */
    private String recipientCountry;
    /**
     * 收件人详细地址，type=2必填
     */
    private String recipientAddress;

    /**
     * 通联配置映射 ID
     */
    private Long tlPayId;

    /**
     * 下单日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;
}
