package ag.orca.living.core.vo.order;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 渠道在直播间订单vo
 *
 * 
 * @date 2024-04-27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道在直播间订单vo")
public class ChannelLivingOrderVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "下单人id")
    private Long id;

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

    @Schema(title = "商品类型 1虚拟 2实物")
    private Integer goodType;

    /**
     * 订单号
     */
    @Schema(title = "订单号")
    private Long orderId;

    /**
     * 下单时间
     */
    @Schema(title = "下单时间")
    private LocalDateTime tradeTime;

    /**
     * 下单商品id
     */
    @Schema(title = "下单商品id")
    private Long goodsId;

    /**
     * 下单商品名称
     */
    @Schema(title = "下单商品名称")
    private String goodsName;

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

    /**
     * 日期
     */
    @Schema(title = "实际付款金额")
    private Long price;


}
