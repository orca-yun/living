package ag.orca.living.core.convert;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.entity.sys.SysRobot;
import ag.orca.living.core.ro.org.OrgBatchRobotRo;
import ag.orca.living.core.ro.org.OrgRobotRo;
import ag.orca.living.core.vo.org.OrgRobotVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class OrgRobotConvert {
    public static OrgRobotVo entityToVo(OrgRobot robot) {
        return Objects.isNull(robot) ? null
                : OrgRobotVo.builder()
                .id(robot.getId())
                .orgId(robot.getOrgId())
                .code(robot.getCode())
                .nickname(robot.getNickname())
                .headIco(robot.getHeadIco())
                .build();
    }

    public static List<OrgRobot> batchRoToEntity(OrgBatchRobotRo ro) {
        Long orgId = ro.getOrgId();
        return CommonConvert.map(ro.getRobotNickNames(), s -> OrgRobot.builder()
                .orgId(orgId)
                .nickname(s)
                .headIco("").build());
    }

    public static OrgRobot roToEntity(OrgRobotRo ro) {
        return Objects.isNull(ro) ? null : OrgRobot.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .nickname(ro.getNickname())
                .headIco(StringUtils.isBlank(ro.getHeadIco()) ? "" : ro.getHeadIco())
                .build();
    }

    public static OrgRobot sysToEntity(SysRobot robot, Long orgId, String code) {
        return OrgRobot.builder()
                .orgId(orgId)
                .code(code)
                .nickname(robot.getNickname())
                .headIco(robot.getHeadIco())
                .build();
    }
}
