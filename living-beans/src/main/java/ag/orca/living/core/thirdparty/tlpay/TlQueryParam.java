package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联订单查询参数
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlQueryParam", description = "通联返回基类")
public class TlQueryParam extends TlBaseParam {
    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号不能为空")
    private String reqsn;

    /**
     * 平台交易流水号
     */
    @Schema(name = "trxid", description = "平台交易流水号")
    private String trxid;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "随机字符串不能为空")
    private String randomstr;
}
