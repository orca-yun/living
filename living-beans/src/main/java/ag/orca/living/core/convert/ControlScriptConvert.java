package ag.orca.living.core.convert;

import ag.orca.living.core.entity.control.ControlScript;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.ro.control.UpdateScriptRo;
import ag.orca.living.core.ro.market.RobotGiftSendRo;
import ag.orca.living.core.vo.control.ControlScriptVo;

import static java.util.Objects.isNull;

public class ControlScriptConvert {

    public static ControlScript robotToScript(LivingRoom room, OrgRobot robot) {
        return ControlScript.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .robotId(robot.getId())
                .robotCode(robot.getCode())
                .robotNickname(robot.getNickname())
                .robotHeadIco(robot.getHeadIco())
                .build();
    }

    public static ControlScriptVo entityToVo(ControlScript entity) {
        return isNull(entity) ?
                null : ControlScriptVo.builder()
                .id(entity.getId())
                .robotCode(entity.getRobotCode())
                .robotNickname(entity.getRobotNickname())
                .robotHeadIco(entity.getRobotHeadIco())
                .messageType(entity.getMessageType())
                .content(entity.getContent())
                .build();
    }

    public static ControlScript roToEntity(ControlScript script, UpdateScriptRo ro) {
        return ControlScript.builder()
                .id(script.getId())
                .messageType(ro.getMessageType())
                .content(ro.getContent())
                .build();
    }


    public static RobotGiftSendRo entityToRobotGiftSendRo(Long giftId, ControlScript script) {
        return RobotGiftSendRo.builder()
                .id(giftId)
                .roomId(script.getRoomId())
                .robotId(script.getRobotId())
                .build();
    }
}
