package ag.orca.living.core.thirdparty.tlpay;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联参数基类
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlBaseParam", description = "通联参数基类")
public class TlBaseParam extends BaseBean {
    /**
     * 集团商户号
     */
    @Schema(name = "orgid", description = "集团商户号")
    private String orgid;

    /**
     * 实际交易商户号
     */
    @Schema(name = "cusid", description = "商户号不能为空")
    private String cusid;

    /**
     * 应用ID
     */
    @Schema(name = "appid", description = "appId不能为空")
    private String appid;

    /**
     * 接口版本号
     */
    @Schema(name = "version", description = "接口版本号")
    private String version;

    /**
     * 签名方式 RSA SM2
     */
    @Schema(name = "signtype", description = "签名方式不能为空")
    private String signtype;

    /**
     * 签名
     */
    @Schema(name = "sign", description = "签名")
    private String sign;
}
