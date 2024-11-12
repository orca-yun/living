package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联订单查询返回
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlQueryResult", description = "通联订单查询返回")
public class TlQueryResult extends TlTranStatusResult {


    /**
     * 交易类型
     */
    @Schema(name = "trxcode", description = "交易类型")
    private String trxcode;

    /**
     * 交易金额
     */
    @Schema(name = "trxamt", description = "交易金额")
    private Long trxamt;


    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号")
    private String reqsn;


    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "商户生产随机字符串")
    private String randomstr;


    /**
     * 支付渠道交易单号
     */
    @Schema(name = "chnltrxid", description = "支付渠道交易单号")
    private String chnltrxid;


    /**
     * 原交易金额
     */
    @Schema(name = "initamt", description = "原交易金额")
    private Long initamt;

    /**
     * 交易完成时间 yyyyMMddHHmmss
     */
    @Schema(name = "fintime", description = "交易完成时间 yyyyMMddHHmmss")
    private String fintime;

    /**
     * 手续费
     */
    @Schema(name = "fee", description = "手续费")
    private Long fee;


    /**
     * 错误原因
     */
    @Schema(name = "errmsg", description = "错误原因")
    private String errmsg;


    /**
     * 渠道子商户号
     */
    @Schema(name = "cmid", description = "渠道子商户号")
    private String cmid;


    /**
     * 渠道号
     */
    @Schema(name = "chnlid", description = "渠道号")
    private String chnlid;


    /**
     * 支付平台用户标识
     */
    @Schema(name = "acct", description = "支付平台用户标识")
    private String acct;


    @Schema(name = "bankcode", description = "银行 CODE")
    private String bankcode;


    /**
     * 支付平台用户标识类型
     */
    @Schema(name = "accttype", description = "支付平台用户标识类型")
    private String accttype;


    /**
     * 渠道信息
     */
    @Schema(name = "chnldata", description = "渠道信息")
    private String chnldata;
}
