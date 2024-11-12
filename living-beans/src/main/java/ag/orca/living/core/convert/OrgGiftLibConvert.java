package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgGiftLib;
import ag.orca.living.core.entity.sys.SysGift;
import ag.orca.living.core.enums.GiftTypeEnum;
import ag.orca.living.core.ro.org.OrgGiftLibRo;
import ag.orca.living.core.vo.org.OrgGiftLibVo;

import java.util.Objects;

public class OrgGiftLibConvert {

    public static OrgGiftLibVo entityToVo(OrgGiftLib lib) {
        return Objects.isNull(lib) ? null
                : OrgGiftLibVo.builder()
                .id(lib.getId())
                .orgId(lib.getOrgId())
                .name(lib.getName())
                .img(lib.getImg())
                .price(lib.getPrice())
                .giftType(lib.getGiftType())
                .build();
    }

    public static OrgGiftLib roToEntity(OrgGiftLibRo ro) {
        return Objects.isNull(ro) ? null
                : OrgGiftLib.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .name(ro.getName())
                .img(ro.getImg())
                .price(ro.getPrice())
                .giftType(ro.getGiftType())
                .build();
    }

    public static OrgGiftLib sysToEntity(SysGift gift, Long orgId) {
        return OrgGiftLib.builder()
                .orgId(orgId)
                .name(gift.getName())
                .img(gift.getImg())
                .price(gift.getPrice())
                .giftType(GiftTypeEnum.STATIC.getCode())
                .build();
    }
}
