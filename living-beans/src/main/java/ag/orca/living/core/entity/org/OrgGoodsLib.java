package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgGoodsLib extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;


    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品类型 1 虚拟商品 2 实物商品
     */
    private Integer goodType;

    /**
     * 原价(分)
     */
    private Long originalPrice;

    /**
     * 现价(分)
     */
    private Long price;

    /**
     * 支付类型 1 跳转支付 2 在线支付 3 小程序支付 4 二维码支付
     */
    private Integer payType;

    /**
     * 小程序支付跳转链接
     */
    private String miniPage;

    /**
     * 跳转链接支付
     */
    private String jumpPage;


    /**
     * 二维码支付图片
     */
    private String qrcode;


    /**
     * 是否限制购买 0 不限制  1 限制
     */
    private Integer bounds;
}
