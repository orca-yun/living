package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomPage;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.MobileLayoutEnum;
import ag.orca.living.core.enums.PcLayoutEnum;
import ag.orca.living.core.ro.room.LivingRoomPageRo;
import ag.orca.living.core.vo.room.LivingRoomPageVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;

import java.util.Objects;

public class LivingRoomPageConvert {

    public static LivingRoomPageVo entityToVo(LivingRoomPage entity) {
        return Objects.isNull(entity) ? null : LivingRoomPageVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .showPpt(entity.getShowPpt())
                .showDraw(entity.getShowDraw())
                .pcSwitch(entity.getPcSwitch())
                .mobileLayout(entity.getMobileLayout())
                .padPcLayout(entity.getPadPcLayout())
                .bgImage(entity.getBgImage())
                .watermark(entity.getWatermark())
                .build();
    }

    public static LivingRoomPage roToEntity(LivingRoomPageRo ro) {
        return Objects.isNull(ro) ? null : LivingRoomPage.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .roomId(ro.getRoomId())
                .showPpt(ro.getShowPpt())
                .showDraw(ro.getShowDraw())
                .pcSwitch(ro.getPcSwitch())
                .mobileLayout(ro.getMobileLayout())
                .padPcLayout(ro.getPadPcLayout())
                .bgImage(ro.getBgImage())
                .watermark(ro.getWatermark())
                .build();
    }

    public static LivingRoomPage buildFromLivingRoom(LivingRoom room) {
        return LivingRoomPage.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .showPpt(BoolEnum.FALSE.getCode())
                .showDraw(BoolEnum.FALSE.getCode())
                .pcSwitch(BoolEnum.TRUE.getCode())
                .mobileLayout(MobileLayoutEnum.FULL_SCREEN.getCode())
                .padPcLayout(PcLayoutEnum.TWO_LAYOUT.getCode())
                .bgImage("")
                .watermark("")
                .build();
    }

    public static LivingRoomShareVo.PageShareVo entityToShareVo(LivingRoomPage page) {
        return Objects.isNull(page) ? null : LivingRoomShareVo.PageShareVo.builder()
                .showPpt(page.getShowPpt())
                .showDraw(page.getShowDraw())
                .padPcLayout(page.getPadPcLayout())
                .mobileLayout(page.getMobileLayout())
                .watermark(page.getWatermark())
                .bgImage(page.getBgImage())
                .build();
    }
}
