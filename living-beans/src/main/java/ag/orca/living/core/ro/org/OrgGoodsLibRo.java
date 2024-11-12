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

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "OrgGoodsLibRo", description = "商品库RO")
public class OrgGoodsLibRo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;


    /**
     * 商品名称
     */
    @Schema(name = "name", description = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品图片
     */
    @Schema(name = "img", description = "商品图片")
    @NotBlank(message = "商品图片不能为空")
    private String img;


    /**
     * 商品类型 1 虚拟商品 2 实物商品
     */
    @Schema(name = "goodType", description = "商品类型 1 虚拟商品 2 实物商品")
    @NotNull(message = "商品类型不能为空")
    private Integer goodType;

    /**
     * 原价(分)
     */
    @Schema(name = "originalPrice", description = "原价(分)")
    @Min(value = 1, message = "现价最低0.01元")
    @Max(value = 9999900, message = "现价最高99999元")
    private Long originalPrice;

    /**
     * 现价(分)
     */
    @Schema(name = "price", description = "现价(分)")
    @Min(value = 1, message = "现价最低0.01元")
    @Max(value = 9999900, message = "现价最高99999元")
    @NotNull(message = "现价不能为空")
    private Long price;

    /**
     * 支付类型 1 跳转支付 2 在线支付 3 小程序支付
     */
    @Schema(name = "payType", description = "支付类型 1 跳转支付 2 在线支付 3 小程序支付 4 扫码支付")
    @NotNull(message = "支付类型不能为空")
    private Integer payType;

    /**
     * 小程序支付跳转链接
     */
    @Schema(name = "miniPage", description = "小程序支付跳转链接")
    private String miniPage;

    /**
     * 跳转链接支付
     */
    @Schema(name = "jumpPage", description = "跳转链接支付")
    private String jumpPage;

    /**
     * 二维码图片
     */
    @Schema(name = "qrcode", description = "二维码地址")
    private String qrcode;

    /**
     * 是否限制购买 0 不限制  1 限制
     */
    @Schema(name = "bounds", description = "是否限制购买 0 不限制  1 限制")
    private Integer bounds;

}
