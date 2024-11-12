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
public class TlTranStatusResult extends TlBaseResult {

    /**
     * 收银宝平台的交易流水号
     */
    @Schema(name = "trxid", description = "收银宝平台的交易流水号")
    private String trxid;

    /**
     * 交易状态
     */
    @Schema(name = "trxstatus", description = "交易状态")
    private String trxstatus;


    public boolean isTrxSuccess() {
        return "0000".equals(trxstatus);
    }

    public boolean isTrxTimeout() {
        return "3045".equals(trxstatus);
    }


}
