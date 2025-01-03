package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联退款返回
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlRefundResult", description = "通联退款返回")
public class TlRefundResult extends TlTranStatusResult {

    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号")
    private String reqsn;

    /**
     * 交易完成时间 yyyyMMddHHmmss
     */
    @Schema(name = "fintime", description = "交易完成时间yyyyMMddHHmmss")
    private String fintime;

    /**
     * 错误原因
     */
    @Schema(name = "errmsg", description = "错误原因")
    private String errmsg;

    /**
     * 手续费
     */
    @Schema(name = "fee", description = "手续费")
    private Long fee;

    /**
     * 交易类型
     */
    @Schema(name = "trxcode", description = "交易类型")
    private String trxcode;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "商户生产随机字符串")
    private String randomstr;
}
