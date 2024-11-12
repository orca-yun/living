package ag.orca.living.core.dao.live;

import ag.orca.living.core.entity.live.LivingTriggerOplog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper
public interface LivingTriggerOplogMapper {
    int insert(LivingTriggerOplog oplog);

    int updateDurationByPushTaskId(@Param("duration") Long duration,
                                   @Param("dateTime") LocalDateTime dateTime,
                                   @Param("pushTaskId") Long pushTaskId);

    int renderPushTaskInfoById(@Param("pushTaskId") Long pushTaskId,
                               @Param("duration") Long duration,
                               @Param("status") Integer status,
                               @Param("dateTime") LocalDateTime dateTime,
                               @Param("id") Long id);

    int updatePushTaskByPushTaskId(@Param("status") Integer status,
                                   @Param("duration") Long duration,
                                   @Param("msg") String msg,
                                   @Param("dateTime") LocalDateTime dateTime,
                                   @Param("pushTaskId") Long pushTaskId);

    LivingTriggerOplog findOplogByPushTaskId(@Param("pushTaskId") Long pushTaskId);

    LivingTriggerOplog findLatestOplogByRoomId(@Param("roomId") Long roomId);

    LivingTriggerOplog findFirstByRoomIdAndDayAndScheduleTime(@Param("roomId") Long roomId,
                                                              @Param("day") LocalDate day,
                                                              @Param("scheduleTime") LocalTime scheduleTime);
}
