package ag.orca.living.core.convert;

import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.LivingTriggerOplogEnum;
import ag.orca.living.core.vo.live.LivingTriggerOplogVo;

import java.time.LocalDate;

public class LivingTriggerOplogConvert {

    public static LivingTriggerOplog buildMediaTriggerOplog(LivingRoom room, LivingMediaLib lib) {
        return LivingTriggerOplog.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .day(LocalDate.now())
                .scheduleTime(room.getScheduleTime())
                .bizId(lib.getId())
                .bizType(LivingTriggerOplogEnum.MEDIA.getCode())
                .pushTaskId(0L)
                .build();
    }

    public static LivingTriggerOplogVo entityToVo(LivingTriggerOplog oplog) {
        return LivingTriggerOplogVo.builder()
                .orgId(oplog.getOrgId())
                .roomId(oplog.getRoomId())
                .day(oplog.getDay())
                .scheduleTime(oplog.getScheduleTime())
                .bizType(oplog.getBizType())
                .pushTaskId(oplog.getPushTaskId())
                .status(oplog.getStatus())
                .duration(oplog.getDuration())
                .msg(oplog.getMsg())
                .build();
    }
}
