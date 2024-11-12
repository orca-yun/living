package ag.orca.living.core.ro.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "OrgGiftLibRo", description = "礼品库RO")
public class OrgGiftLibRo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    /**
     * 礼物名称
     */
    @Schema(name = "name", description = "礼物名称")
    @NotBlank(message = "礼物名称不能为空")
    @Length(min = 1, max = 6, message = "礼物名称最大字数6")
    private String name;

    /**
     * 礼物图片
     */
    @Schema(name = "img", description = "礼物图片")
    private String img;

    /**
     * 礼物价格(分)
     */
    @Schema(name = "price", description = "礼物价格(分)")
    @Min(value = 0, message = "礼物价格不能低于0元")
    @Max(value = 99999, message = "礼物价格最高999.99元")
    @NotNull(message = "礼物价格不能为空")
    private Long price;


    /**
     * 礼物类型 1 静态礼物 2 动态礼物
     */
    @Schema(name = "giftType", description = "礼物类型 1 静态礼物 2 动态礼物")
    @NotNull(message = "礼物类型不能为空")
    private Integer giftType;
}
