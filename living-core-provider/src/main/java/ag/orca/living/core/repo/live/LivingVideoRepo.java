package ag.orca.living.core.repo.live;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.enums.PushTaskStatusEnum;
import ag.orca.living.core.event.VideoPushTaskStatusEvent;
import ag.orca.living.core.event.VideoStartLiveEvent;
import ag.orca.living.core.event.VideoStopLiveEvent;
import ag.orca.living.core.repo.media.LivingMediaLibRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.stream.StreamManageRepo;
import ag.orca.living.core.vo.stream.LivingStreamPushAddressVo;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static ag.orca.living.core.convert.VideoTriggerConvert.roToVideoStartLiveEvent;

@Slf4j
@Repository
public class LivingVideoRepo {

    @Resource
    LivingMediaLibRepo mediaLibRepo;

    @Resource
    LivingRoomRepo livingRoomRepo;

    @Resource
    StreamManageRepo streamManageRepo;

    @Resource
    LivingTriggerOplogRepo triggerOplogRepo;

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    Producer<VideoStartLiveEvent> videoStartLiveEventProducer;

    @Resource
    Producer<VideoStopLiveEvent> videoStopLiveEventProducer;

    public Optional<LivingTriggerOplog> findLatestLivingTriggerOplogByRoomId(Long roomId) {
        return triggerOplogRepo.findLatestByRoomId(roomId);
    }

    /**
     * 通过视频的触发新的开播记录
     *
     * @param roomId  房间ID
     * @param mediaId 媒体库ID
     */
    public void videoOperateLiveStart(Long roomId, Long mediaId, Integer times, Long targetDuration, LiveSourceEnum liveSource) {
        Optional<LivingRoom> optionalRoom = livingRoomRepo.findLivingRoomById(roomId);
        Optional<LivingMediaLib> optionalMedia = mediaLibRepo.findMediaById(mediaId);
        OrcaAssert.match(optionalRoom.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        OrcaAssert.match(optionalMedia.isEmpty(), I18nUtil.getMessage("media.lib.not.exist"));
        // 判断房间在不在直播过程中
        optionalMedia.ifPresent(lib -> {
            // 保存 视频触发的开播操作记录【有状态的】
            LivingTriggerOplog oplog = triggerOplogRepo.saveOplog(optionalRoom.get(), lib);
            LivingStreamPushAddressVo vo = streamManageRepo.buildPushAddress(roomId);
            VideoStartLiveEvent event = roToVideoStartLiveEvent(roomId, times, targetDuration, vo.getRtmp(), lib, oplog, liveSource);
            videoStartLiveEventProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[开始视频直播:普通] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
        });
    }

//    public void videoOperateLiveStop(Long roomId, LiveSourceEnum liveSource) {
//        Optional<LivingRoom> optionalRoom = livingRoomRepo.findLivingRoomById(roomId);
//        OrcaAssert.match(optionalRoom.isEmpty(), I18nUtil.getMessage("room.not.exists"));
//        // 查询正在直播的ID，看直播来源是哪里的
//        Optional<LivingRoomLiveRecord> optional = livingLiveRepo.findLatestLiveRecordByRoomId(roomId);
//        optional.ifPresent(record -> {
//            LiveSourceEnum source = LiveSourceEnum.ofCode(record.getLiveSource());
//            OrcaAssert.match(source != liveSource, MessageFormat.format(I18nUtil.getMessage("room.record.live.stop.err"), source, liveSource));
//            VideoStopLiveEvent event = roToVideoStopLiveEvent(roomId, liveSource);
//            videoStopLiveEventProducer.sendAsync(event)
//                    .thenAccept(messageId ->
//                            log.info("[停止视频直播:普通] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
//        });
//    }


    @Transactional(rollbackFor = Exception.class)
    public void autoStartLive(VideoPushTaskStatusEvent event) {
        Optional<LivingTriggerOplog> optional = triggerOplogRepo.findOplogByPushTaskId(event.getPushTaskId());
        optional.ifPresent(l -> {
            // 更新状态
            triggerOplogRepo.updatePushTaskByPushTaskId(event);
            LiveSourceEnum source = LiveSourceEnum.ofCode(event.getLiveSource());
            // 执行开播操作
            livingLiveRepo.operateLiveStart(event.getRoomId(), source, BoolEnum.FALSE.getCode());
        });
    }

    /**
     * 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败
     *
     * @param event
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoStopLive(VideoPushTaskStatusEvent event) {
        Optional<LivingTriggerOplog> optional = triggerOplogRepo.findOplogByPushTaskId(event.getPushTaskId());
        optional.ifPresent(l -> {
            PushTaskStatusEnum status = PushTaskStatusEnum.ofCode(l.getStatus());
            if (!status.isEnd()) {
                // 更新状态
                triggerOplogRepo.updatePushTaskByPushTaskId(event);
                LiveSourceEnum source = LiveSourceEnum.ofCode(event.getLiveSource());
                // 执行停止直播
                Optional<Long> longOptional = livingLiveRepo.getLivingRecordIdByRoomId(event.getRoomId());
                longOptional.ifPresent(recordId -> livingLiveRepo.operateLiveStop(event.getRoomId(), source));
            }
        });
    }
}
