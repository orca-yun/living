package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgGoodsLib;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.GoodTypeEnum;
import ag.orca.living.core.ro.org.OrgGoodsLibRo;
import ag.orca.living.core.vo.org.OrgGoodsLibVo;

import java.util.Objects;

public class OrgGoodsLibConvert {

    public static OrgGoodsLibVo entityToVo(OrgGoodsLib lib) {
        return Objects.isNull(lib) ? null : OrgGoodsLibVo.builder()
                .id(lib.getId())
                .orgId(lib.getOrgId())
                .name(lib.getName())
                .img(lib.getImg())
                .goodType(lib.getGoodType())
                .originalPrice(lib.getOriginalPrice())
                .price(lib.getPrice())
                .payType(lib.getPayType())
                .miniPage(lib.getMiniPage())
                .jumpPage(lib.getJumpPage())
                .qrcode(lib.getQrcode())
                .bounds(lib.getBounds())
                .build();
    }

    public static OrgGoodsLib roToEntity(OrgGoodsLibRo ro) {
        return Objects.isNull(ro) ? null
                : OrgGoodsLib.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .name(ro.getName())
                .img(ro.getImg())
                .goodType(Objects.isNull(ro.getGoodType()) ? GoodTypeEnum.VIRTUAL.getCode() : ro.getGoodType())
                .originalPrice(ro.getOriginalPrice())
                .price(ro.getPrice())
                .payType(ro.getPayType())
                .miniPage(ro.getMiniPage())
                .jumpPage(ro.getJumpPage())
                .qrcode(ro.getQrcode())
                .bounds(Objects.isNull(ro.getBounds()) ? BoolEnum.FALSE.getCode() : ro.getBounds())
                .build();
    }
}
