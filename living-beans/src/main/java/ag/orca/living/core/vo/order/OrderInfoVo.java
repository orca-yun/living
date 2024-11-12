package ag.orca.living.core.vo.order;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户订单实物收获信息vo
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrderInfoVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "订单ID")
    private Long id;


    @Schema(title = "订单号")
    private String orderNo;

    /**
     * 支付流水号
     */
    @Schema(title = "支付流水号")
    private String transactionId;

    /**
     * 订单发生的直播间id
     */
    @Schema(title = "下单房间ID")
    private Long roomId;

    /**
     * 订单来源渠道id，目前是6位数字
     */
    @Schema(title = "渠道ID")
    private Long channelId;

    /**
     * 交易商品id
     */
    @Schema(title = "下单商品id")
    private Long goodsId;
    /**
     * 下单人ID
     */
    @Schema(title = "下单人ID")
    private Long userId;
    /**
     * 下单人名称
     */
    @Schema(title = "下单人名称")
    private String userName;

    @Schema(title = "订单超时时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeoutTime;

    @Schema(title = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderTime;

    @Schema(title = "成交时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeTime;


    @Schema(title = "退款时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTime;


    @Schema(title = "取消订单时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelTime;


    @Schema(title = "商品类型 1 虚拟商品 2 实物商品")
    private Integer type;

    /**
     * 商品原价
     */
    @Schema(title = "商品原价")
    private Long originalPrice;


    /**
     * 使用的优惠卷
     */
    @Schema(title = "使用的优惠卷")
    private String coupon;


    @Schema(title = "实际付款的金额(分)")
    private Long price;

    @Schema(title = "实际付款的金额(分)")
    private Long realAmt;


    /**
     * 当前渠道的分佣比例
     */
    @Schema(title = "当前渠道的分佣比例")
    private Integer commissionRatio;
    /**
     * 当前渠道的支付手续费
     */
    @Schema(title = "当前渠道的支付手续费")
    private Integer paymentFee;
    /**
     * 订单分佣金额
     */
    @Schema(title = "订单分佣金额")
    private Long subCommission;
    /**
     * 实际分账金额(subCommission * (1-paymentFee)/100)
     */
    @Schema(title = "实际分账金额")
    private Long actualAccountingAmount;
    /**
     * 订单状态 1待支付 2已支付 3已取消
     */
    @Schema(title = "订单状态 1待支付 2已支付 3已取消 4超时未支付")
    private Integer orderStatus;


    /**
     * 下单填写的用户姓名
     */
    @Schema(title = "下单填写的用户姓名")
    private String recipientName;

    /**
     * 下单填写的用户手机号
     */
    @Schema(title = "下单填写的用户手机号")
    private String recipientPhone;


    @Schema(title = "收件人详细地址")
    private String recipientAddress;

    /**
     * 下单日期
     */
    @Schema(title = "下单日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;


}
