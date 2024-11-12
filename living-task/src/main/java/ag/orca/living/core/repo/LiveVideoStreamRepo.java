package ag.orca.living.core.repo;

import ag.orca.living.core.convert.VideoTriggerConvert;
import ag.orca.living.core.dao.live.LivingTriggerOplogMapper;
import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.event.VideoStreamTaskEvent;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Slf4j
@Repository
public class LiveVideoStreamRepo {

    @Resource
    LivingTriggerOplogMapper livingTriggerOplogMapper;

    @Resource
    Producer<VideoStreamTaskEvent> videoStreamTaskEventProducer;


    public void processRoomVideoStream(LocalDateTime trigger, LivingRoom room) {
        if (hit(trigger, room)) {
            VideoStreamTaskEvent event = VideoTriggerConvert.buildVideoStreamTaskEvent(room, LiveSourceEnum.VIDEO_PUSH);
            videoStreamTaskEventProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[触发任务视频直播事件] roomId: {}, msg: {}", room.getId(), JsonUtil.beanToJson(event)));
        }
    }

    private boolean hit(LocalDateTime trigger, LivingRoom room) {
        if (Objects.nonNull(room.getScheduleTime()) && Objects.nonNull(room.getMediaBizId())) {
            LocalTime time = trigger.toLocalTime();
            LocalTime scheduleTime = room.getScheduleTime();
            if (!time.isBefore(scheduleTime)) {
                LivingTriggerOplog oplog = livingTriggerOplogMapper.findFirstByRoomIdAndDayAndScheduleTime(room.getId(), trigger.toLocalDate(), room.getScheduleTime());
                return Objects.isNull(oplog);
            }
        }
        return false;
    }
}
