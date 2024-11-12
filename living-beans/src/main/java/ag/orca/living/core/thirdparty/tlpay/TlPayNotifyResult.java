package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlPayNotifyResult", description = "通联回调")
public class TlPayNotifyResult extends TlTranStatusResult {

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
     * 手续费
     */
    @Schema(name = "fee", description = "手续费")
    private Long fee;


    /**
     * 交易金额
     */
    @Schema(name = "trxamt", description = "交易金额")
    private Long trxamt;

    /**
     * 交易类型
     */
    @Schema(name = "trxcode", description = "交易类型")
    private String trxcode;

    private String paytime;

    private String termauthno;

    private String termrefnum;

    private String termtraceno;

    private String trxdate;

    private String outtrxid;

    private String cusorderid;


}
