package ag.orca.living.core.vo.share;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingShareUserAddressVo extends BaseBean {

    /**
     * 收件人姓名，type=2必填
     */
    @Schema(name = "recipientName", description = "收件人姓名")
    private String recipientName;
    /**
     * 收件人手机号，type=2必填
     */
    @Schema(name = "recipientPhone", description = "收件人手机号")
    private String recipientPhone;
    /**
     * 收件人所在省份，type=2必填
     */
    @Schema(name = "recipientProvince", description = "收件人所在省份")
    private String recipientProvince;
    /**
     * 收件人所在城市，type=2必填
     */
    @Schema(name = "recipientCity", description = "收件人所在城市")
    private String recipientCity;
    /**
     * 收件人所在县区，type=2必填
     */
    @Schema(name = "recipientCountry", description = "收件人所在县区")
    private String recipientCountry;
    /**
     * 收件人详细地址，type=2必填
     */
    @Schema(name = "recipientAddress", description = "收件人详细地址")
    private String recipientAddress;
}
