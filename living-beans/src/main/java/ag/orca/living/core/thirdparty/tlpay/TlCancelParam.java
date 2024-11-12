package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联撤销支付参数
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlCancelParam", description = "通联返回基类")
public class TlCancelParam extends TlBaseParam {
    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号不能为空")
    private String reqsn;

    /**
     * 交易金额，单位为分
     */

    @Schema(name = "trxamt", description = "交易金额不能为空")
    private Long trxamt;

    /**
     * 原商户交易订单号
     */
    @Schema(name = "oldreqsn", description = "原商户交易订单号")
    private String oldreqsn;

    /**
     * 原交易的收银宝平台流水号
     */
    @Schema(name = "oldtrxid", description = "原交易的收银宝平台流水号")
    private String oldtrxid;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "oldReqSn", description = "随机字符串不能为空")
    private String randomstr;
}
