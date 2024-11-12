package ag.orca.living.core.thirdparty.tlpay;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联返回基类
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlBaseResult", description = "通联返回基类")
public class TlBaseResult extends BaseBean {
    /**
     * 返回码 SUCCESS/FAIL
     */
    @Schema(name = "retcode", description = "返回码 SUCCESS/FAIL")
    private String retcode;

    /**
     * 返回码说明
     */
    @Schema(name = "retmsg", description = "返回码说明")
    private String retmsg;

    //以下信息只有当retcode为SUCCESS时有返回

    /**
     * 实际交易商户号
     */
    @Schema(name = "cusid", description = "实际交易商户号")
    private String cusid;

    /**
     * 应用ID
     */
    @Schema(name = "appid", description = "应用ID")
    private String appid;

    /**
     * 签名
     */
    @Schema(name = "sign", description = "签名")
    private String sign;


    public boolean isFailed() {
        return "FAIL".equals(retcode);
    }
}
