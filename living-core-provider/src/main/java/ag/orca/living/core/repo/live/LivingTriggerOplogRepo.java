package ag.orca.living.core.repo.live;


import ag.orca.living.core.convert.LivingTriggerOplogConvert;
import ag.orca.living.core.dao.live.LivingTriggerOplogMapper;
import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.event.VideoPushTaskStatusEvent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class LivingTriggerOplogRepo {


    @Resource
    LivingTriggerOplogMapper triggerOplogMapper;

    @Transactional(rollbackFor = Exception.class)
    public LivingTriggerOplog saveOplog(LivingRoom room, LivingMediaLib lib) {
        LivingTriggerOplog oplog = LivingTriggerOplogConvert.buildMediaTriggerOplog(room, lib);
        triggerOplogMapper.insert(oplog);
        return oplog;
    }


    @Transactional(rollbackFor = Exception.class)
    public void renderPushTaskInfoById(VideoPushTaskStatusEvent event, Long oplogId) {
        triggerOplogMapper.renderPushTaskInfoById(
                event.getPushTaskId(),
                event.getDuration(),
                event.getStatus(),
                LocalDateTime.now(), oplogId);

    }

    public Optional<LivingTriggerOplog> findOplogByPushTaskId(Long pushTaskId) {
        LivingTriggerOplog oplog = triggerOplogMapper.findOplogByPushTaskId(pushTaskId);
        return Optional.ofNullable(oplog);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDurationByPushTaskId(Long duration, Long pushTaskId) {
        findOplogByPushTaskId(pushTaskId).ifPresent(oplog ->
                triggerOplogMapper.updateDurationByPushTaskId(duration, LocalDateTime.now(), pushTaskId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePushTaskByPushTaskId(VideoPushTaskStatusEvent event) {
        triggerOplogMapper.updatePushTaskByPushTaskId(
                event.getStatus(),
                event.getDuration(),
                event.getMsg(),
                LocalDateTime.now(),
                event.getPushTaskId());
    }


    public Optional<LivingTriggerOplog> findLatestByRoomId(Long roomId) {
        return Optional.ofNullable(triggerOplogMapper.findLatestOplogByRoomId(roomId));
    }
}
