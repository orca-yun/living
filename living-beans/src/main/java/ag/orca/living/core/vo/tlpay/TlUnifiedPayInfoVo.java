package ag.orca.living.core.vo.tlpay;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * <p>
 * 通联统一支付VO
 * </p>
 */

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道数据看板VO")
public class TlUnifiedPayInfoVo extends BaseBean {
    /**
     * 订单号
     */
    @Schema(title = "订单号")
    private String orderNo;

    /**
     * 支付金额
     */
    @Schema(title = "支付金额")
    private BigDecimal payTotal;

    /**
     * 支付类型
     */
    @Schema(title = "支付类型")
    private Integer payType;

    /**
     * 通联返回的支付信息（扫码支付则返回二维码串，js支付则返回json字符串,QQ钱包及云闪付的JS支付返回支付的链接,商户只需跳转到此链接即可完成支付,支付宝App支付返回支付信息串）
     */
    @Schema(title = "通联返回的支付信息")
    private String payInfo;

}
