package ag.orca.living.provider.live;

import ag.orca.living.api.live.LivingLiveService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.LivingRoomLiveRecordConvert;
import ag.orca.living.core.convert.LivingTriggerOplogConvert;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.enums.LivingTriggerOplogStatusEnum;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.live.LivingTriggerOplogRepo;
import ag.orca.living.core.repo.live.LivingVideoRepo;
import ag.orca.living.core.repo.tencent.LiveOperateCloudRepo;
import ag.orca.living.core.vo.live.LivingRoomLiveRecordVo;
import ag.orca.living.core.vo.live.LivingTriggerOplogVo;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Optional;

@Slf4j
@DubboService
public class LivingLiveProvider implements LivingLiveService {

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    LivingVideoRepo livingVideoRepo;

    @Resource
    LivingTriggerOplogRepo triggerOplogRepo;

    @Resource
    LiveOperateCloudRepo liveOperateCloudRepo;


    @Override
    public LivingRoomLiveRecordVo findLatestLiveRecord(Long roomId) {
        LivingRoomLiveRecord record = livingLiveRepo.findLatestLiveRecord(roomId);
        return LivingRoomLiveRecordConvert.entityToVo(record);
    }

    @Override
    public Optional<LivingTriggerOplogVo> latestVideoOplog(Long roomId) {
        Optional<LivingTriggerOplog> oplog = livingVideoRepo.findLatestLivingTriggerOplogByRoomId(roomId);
        return oplog.map(LivingTriggerOplogConvert::entityToVo);
    }

    @Override
    public void manualOperateLiveStart(Long roomId, Integer needRecord, LiveSourceEnum liveSource) {
        Optional<LivingTriggerOplog> oplog = triggerOplogRepo.findLatestByRoomId(roomId);
        oplog.ifPresent(p -> OrcaAssert.match(LivingTriggerOplogStatusEnum.isNotEnd(p.getStatus()), I18nUtil.getMessage("room.record.live.video.exec.err")));
        livingLiveRepo.operateLiveStart(roomId, liveSource, needRecord);
    }

    @Override
    public void manualOperateLiveStop(Long roomId, LiveSourceEnum liveSource) {
        livingLiveRepo.operateLiveStop(roomId, liveSource);
        liveOperateCloudRepo.queryAndDropLiveStream(roomId.toString());
    }

//    @Override
//    public void videoOperateLiveStart(Long roomId, LiveVideoOperateRo ro, LiveSourceEnum liveSource) {
//        livingVideoRepo.videoOperateLiveStart(roomId, ro.getMediaId(), ro.getTimes(), ro.getTargetDuration(), liveSource);
//    }

//    @Override
//    public void videoOperateLiveStop(Long roomId, LiveSourceEnum liveSource) {
//        livingVideoRepo.videoOperateLiveStop(roomId, liveSource);
//    }


}
