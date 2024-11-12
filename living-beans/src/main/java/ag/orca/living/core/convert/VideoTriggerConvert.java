package ag.orca.living.core.convert;

import ag.orca.living.core.entity.live.LivingTriggerOplog;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.event.VideoStartLiveEvent;
import ag.orca.living.core.event.VideoStopLiveEvent;
import ag.orca.living.core.event.VideoStreamTaskEvent;

public class VideoTriggerConvert {


    public static VideoStartLiveEvent roToVideoStartLiveEvent(Long roomId,
                                                              Integer times,
                                                              Long targetDuration,
                                                              String rtmp,
                                                              LivingMediaLib lib,
                                                              LivingTriggerOplog oplog,
                                                              LiveSourceEnum liveSource) {
        return VideoStartLiveEvent.builder()
                .roomId(roomId)
                .rtmp(rtmp)
                .times(times)
                .targetDuration(targetDuration)
                .fileName(lib.getFileName())
                .bucket(lib.getBucket())
                .pathName(lib.getPathName())
                .mediaType(lib.getMediaType())
                .mediaRoomId(lib.getRoomId())
                .mediaRoomRecordId(lib.getRoomRecordId())
                .oplogId(oplog.getId().toString())
                .liveSource(liveSource.getCode())
                .build();
    }

    public static VideoStopLiveEvent roToVideoStopLiveEvent(Long roomId,
                                                            LiveSourceEnum liveSource) {
        return VideoStopLiveEvent.builder()
                .roomId(roomId)
                .liveSource(liveSource.getCode())
                .build();
    }

    public static VideoStreamTaskEvent buildVideoStreamTaskEvent(LivingRoom room, LiveSourceEnum liveSource) {
        return VideoStreamTaskEvent.builder()
                .roomId(room.getId())
                .mediaId(room.getMediaBizId())
                .liveSource(liveSource)
                .times(room.getCycleTimes())
                .targetDuration(0L)
                .build();
    }
}
