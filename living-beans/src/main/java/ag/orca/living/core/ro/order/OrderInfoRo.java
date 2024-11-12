package ag.orca.living.core.ro.order;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 创建订单Ro
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "OrderInfoRo", description = "创建订单Ro")
public class OrderInfoRo extends BaseBean {

    /**
     * 交易商品id
     */
    @Schema(name = "goodsId", description = "交易商品id")
    @NotNull(message = "交易商品ID不能为空")
    private Long goodsId;


    /**
     * 使用的优惠券
     */
    @Schema(name = "coupon", description = "使用的优惠券")
    private String coupon;

    /**
     * 收件人姓名，type=2必填
     */
    @Schema(name = "recipientName", description = "收件人姓名，虚拟和实物都必填")
    private String recipientName;
    /**
     * 收件人手机号，type=2必填
     */
    @Schema(name = "recipientPhone", description = "收件人手机号,虚拟和实物都必填")
    private String recipientPhone;
    /**
     * 收件人所在省份，type=2必填
     */
    @Schema(name = "recipientProvince", description = "收件人所在省份，type=2必填")
    private String recipientProvince;
    /**
     * 收件人所在城市，type=2必填
     */
    @Schema(name = "recipientCity", description = "收件人所在城市，type=2必填")
    private String recipientCity;
    /**
     * 收件人所在县区，type=2必填
     */
    @Schema(name = "recipientCountry", description = "收件人所在县区，type=2必填")
    private String recipientCountry;
    /**
     * 收件人详细地址，type=2必填
     */
    @Schema(name = "recipientAddress", description = "收件人详细地址，type=2必填")
    private String recipientAddress;
}
