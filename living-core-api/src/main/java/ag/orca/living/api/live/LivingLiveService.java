package ag.orca.living.api.live;

import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.vo.live.LivingRoomLiveRecordVo;
import ag.orca.living.core.vo.live.LivingTriggerOplogVo;

import java.util.Optional;

public interface LivingLiveService {


    /**
     * 手动操作直播
     *
     * @param roomId
     * @param needRecord
     * @param
     */
    void manualOperateLiveStart(Long roomId, Integer needRecord, LiveSourceEnum liveSource);

    /**
     * 手动操作直播
     *
     * @param roomId
     */
    void manualOperateLiveStop(Long roomId, LiveSourceEnum liveSource);

//    /**
//     * 视频直播(触发)
//     *
//     * @param roomId
//     * @param ro
//     */
//    void videoOperateLiveStart(Long roomId, LiveVideoOperateRo ro, LiveSourceEnum liveSource);
//
//    /**
//     * 视频关播(主动)
//     *
//     * @param roomId
//     */
//    void videoOperateLiveStop(Long roomId, LiveSourceEnum liveSource);


    LivingRoomLiveRecordVo findLatestLiveRecord(Long roomId);

    /**
     * 找寻该房间最近一次视频直播的操作记录
     *
     * @param roomId
     * @return
     */
    Optional<LivingTriggerOplogVo> latestVideoOplog(Long roomId);


}
