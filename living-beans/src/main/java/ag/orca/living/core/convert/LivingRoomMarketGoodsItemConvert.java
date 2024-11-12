package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgGoodsLib;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.SellStatusEnum;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;

import java.util.Objects;

public class LivingRoomMarketGoodsItemConvert {

    public static LivingRoomMarketGoodsItemVo entityToVo(LivingRoomMarketGoodsItem entity) {
        return Objects.isNull(entity) ? null
                : LivingRoomMarketGoodsItemVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .goodsLibId(entity.getGoodsLibId())
                .name(entity.getName())
                .img(entity.getImg())
                .goodType(entity.getGoodType())
                .originalPrice(entity.getOriginalPrice())
                .price(entity.getPrice())
                .payType(entity.getPayType())
                .miniPage(entity.getMiniPage())
                .jumpPage(entity.getJumpPage())
                .sellStatus(entity.getSellStatus())
                .priority(entity.getPriority())
                .qrcode(entity.getQrcode())
                .bounds(entity.getBounds())
                .build();
    }


    public static LivingRoomMarketGoodsItem libToEntity(Long roomId, OrgGoodsLib lib) {
        return Objects.isNull(lib) ? null
                : LivingRoomMarketGoodsItem.builder()
                .orgId(lib.getOrgId())
                .roomId(roomId)
                .goodsLibId(lib.getId())
                .name(lib.getName())
                .img(lib.getImg())
                .goodType(lib.getGoodType())
                .originalPrice(lib.getOriginalPrice())
                .price(lib.getPrice())
                .payType(lib.getPayType())
                .miniPage(lib.getMiniPage())
                .jumpPage(lib.getJumpPage())
                .qrcode(lib.getQrcode())
                .sellStatus(SellStatusEnum.ON_SHELVES.getCode())
                .priority(BoolEnum.FALSE.getCode())
                .bounds(Objects.isNull(lib.getBounds()) ? BoolEnum.FALSE.getCode() : lib.getBounds())
                .build();
    }

}
