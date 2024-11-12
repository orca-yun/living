package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomInteract;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.ro.room.LivingRoomInteractRo;
import ag.orca.living.core.vo.room.LivingRoomInteractVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;

import java.util.Objects;

public class LivingRoomInteractConvert {

    public static LivingRoomInteractVo entityToVo(LivingRoomInteract entity) {
        return Objects.isNull(entity) ? null : LivingRoomInteractVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .loginTips(entity.getLoginTips())
                .allMute(entity.getAllMute())
                .msgApprove(entity.getMsgApprove())
                .exemptEnable(entity.getExemptEnable())
                .exemptWordId(entity.getExemptWordId())
                .tupleEnable(entity.getTupleEnable())
                .tupleNum(entity.getTupleNum())
                .build();
    }

    public static LivingRoomInteract roToEntity(LivingRoomInteractRo ro) {
        return Objects.isNull(ro) ? null : LivingRoomInteract.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .roomId(ro.getRoomId())
                .loginTips(ro.getLoginTips())
                .allMute(ro.getAllMute())
                .msgApprove(ro.getMsgApprove())
                .exemptEnable(ro.getExemptEnable())
                .exemptWordId(ro.getExemptWordId())
                .tupleEnable(ro.getTupleEnable())
                .tupleNum(ro.getTupleNum())
                .build();
    }

    public static LivingRoomInteract buildFromLivingRoomInteract(LivingRoom room) {
        return LivingRoomInteract.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .loginTips(BoolEnum.FALSE.getCode())
                .allMute(BoolEnum.FALSE.getCode())
                .msgApprove(BoolEnum.TRUE.getCode())
                .exemptEnable(BoolEnum.FALSE.getCode())
                .exemptWordId(null)
                .tupleEnable(BoolEnum.FALSE.getCode())
                .tupleNum(1)
                .build();
    }

    public static LivingRoomShareVo.InteractShareVo entityToShareVo(LivingRoomInteract interact) {

        return Objects.isNull(interact) ? null :
                LivingRoomShareVo.InteractShareVo
                        .builder()
                        .tupleEnable(interact.getTupleEnable())
                        .tupleNum(interact.getTupleNum())
                        .build();
    }
}
