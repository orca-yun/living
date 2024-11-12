package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联退款参数
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlRefundParam", description = "通联返回基类")
public class TlRefundParam extends TlBaseParam {
    /**
     * 退款金额
     */
    @Schema(name = "trxamt", description = "退款金额不能为空")
    private Long trxamt;

    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号不能为空")
    private String reqsn;

    /**
     * 原商户交易订单号
     */
    @Schema(name = "oldreqsn", description = "原商户交易订单号")
    private String oldreqsn;

    /**
     * 原交易的收银宝平台流水号
     */
    @Schema(name = "oldtrxid", description = "原商户交易订单号")
    private String oldtrxid;

    /**
     * 优惠信息
     */
    @Schema(name = "benefitdetail", description = "原商户交易订单号")
    private String benefitdetail;

    /**
     * 备注
     */
    @Schema(name = "remark", description = "原商户交易订单号")
    private String remark;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "随机字符串不能为空")
    private String randomstr;

}
