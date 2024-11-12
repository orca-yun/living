package ag.orca.living.core.vo.org;

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
public class OrgGoodsLibVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 商品名称
     */
    @Schema(name = "name", description = "商品名称")
    private String name;

    /**
     * 商品图片
     */
    @Schema(name = "img", description = "商品图片")
    private String img;

    /**
     * 商品类型 1 虚拟商品 2 实物商品
     */
    @Schema(name = "goodType", description = "商品类型 1 虚拟商品 2 实物商品")
    private Integer goodType;

    /**
     * 原价(分)
     */
    @Schema(name = "originalPrice", description = "原价(分)")
    private Long originalPrice;

    /**
     * 现价(分)
     */
    @Schema(name = "price", description = "现价(分)")
    private Long price;

    /**
     * 支付类型 1 跳转支付 2 在线支付 3 小程序支付  4 扫码支付
     */
    @Schema(name = "payType", description = "支付类型 1 跳转支付 2 在线支付 3 小程序支付  4 扫码支付")
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
