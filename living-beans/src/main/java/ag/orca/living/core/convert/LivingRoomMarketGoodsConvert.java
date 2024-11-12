package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomMarketGoods;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.ShowStyleEnum;
import ag.orca.living.core.ro.room.LivingRoomMarketGoodsRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;

import java.util.Objects;

import static ag.orca.living.common.OrcaConst.MARKET_GOODS_BTN_CONTENT;

public class LivingRoomMarketGoodsConvert {

    public static LivingRoomMarketGoodsVo entityToVo(LivingRoomMarketGoods entity) {
        return Objects.isNull(entity) ? null : LivingRoomMarketGoodsVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .btnTxt(entity.getBtnTxt())
                .countDown(entity.getCountDown())
                .showStyle(entity.getShowStyle())
                .build();
    }

    public static LivingRoomMarketGoods roToEntity(LivingRoomMarketGoodsRo ro) {
        return Objects.isNull(ro) ? null : LivingRoomMarketGoods.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .roomId(ro.getRoomId())
                .btnTxt(ro.getBtnTxt())
                .countDown(ro.getCountDown())
                .showStyle(ro.getShowStyle())
                .build();


    }

    public static LivingRoomMarketGoods buildFromLivingRoom(LivingRoom room) {
        return LivingRoomMarketGoods.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .btnTxt(MARKET_GOODS_BTN_CONTENT)
                .countDown(BoolEnum.FALSE.getCode())
                .showStyle(ShowStyleEnum.MIN.getCode())
                .build();
    }

    public static LivingRoomShareVo.GoodsShareVo entityToShareVo(LivingRoomMarketGoods goods) {
        return Objects.isNull(goods) ? null : LivingRoomShareVo.GoodsShareVo.builder()
                .btnTxt(goods.getBtnTxt())
                .countDown(goods.getCountDown())
                .showStyle(goods.getShowStyle())
                .build();
    }
}
