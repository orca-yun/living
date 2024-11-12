package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联统一支付返回
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlUnifiedPayResult", description = "通联统一支付返回")
public class TlUnifiedPayResult extends TlTranStatusResult {

    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号")
    private String reqsn;

    /**
     * 交易完成时间 yyyyMMddHHmmss
     */
    @Schema(name = "fintime", description = "交易完成时间 yyyyMMddHHmmss")
    private String fintime;

    /**
     * 错误原因
     */
    @Schema(name = "errmsg", description = "错误原因")
    private String errmsg;

    /**
     * 支付串
     */
    @Schema(name = "payinfo", description = "支付串")
    private String payinfo;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "商户生产随机字符串")
    private String randomstr;

    /**
     * 渠道门店编号
     * 例如对于支付宝支付，支付宝门店编号,对于微信支付，微信门店编号
     */
    @Schema(name = "chnlstoreid", description = "渠道门店编号")
    private String chnlstoreid;

    /**
     * 交易金额
     */
    @Schema(name = "chnlstoreid", description = "交易金额")
    private Long trxamt;

    /**
     * 支付时间
     */
    @Schema(name = "paytime", description = "支付时间")
    private String paytime;


}
