package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgGiftLib;
import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.GiftStatusEnum;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;

import java.util.Objects;

public class LivingRoomMarketGiftItemConvert {

    public static LivingRoomMarketGiftItemVo entityToVo(LivingRoomMarketGiftItem entity) {
        return Objects.isNull(entity) ? null : LivingRoomMarketGiftItemVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .giftLibId(entity.getGiftLibId())
                .name(entity.getName())
                .img(entity.getImg())
                .price(entity.getPrice())
                .giftType(entity.getGiftType())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .build();
    }


    public static LivingRoomMarketGiftItem libToEntity(Long roomId, OrgGiftLib lib) {
        return Objects.isNull(lib) ? null : LivingRoomMarketGiftItem.builder()
                .orgId(lib.getOrgId())
                .roomId(roomId)
                .giftLibId(lib.getId())
                .name(lib.getName())
                .img(lib.getImg())
                .price(lib.getPrice())
                .giftType(lib.getGiftType())
                .status(GiftStatusEnum.ON_SHELVES.getCode())
                .priority(BoolEnum.FALSE.getCode())
                .build();
    }

}
