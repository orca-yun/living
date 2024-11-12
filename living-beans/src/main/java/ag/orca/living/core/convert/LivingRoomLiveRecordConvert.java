package ag.orca.living.core.convert;

import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.vo.live.LivingRoomLiveRecordVo;
import ag.orca.living.core.vo.live.LivingStatusVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class LivingRoomLiveRecordConvert {
    public static LivingRoomLiveRecordVo entityToVo(LivingRoomLiveRecord record) {
        return Objects.isNull(record) ? null
                : LivingRoomLiveRecordVo.builder()
                .id(record.getId())
                .orgId(record.getOrgId())
                .roomId(record.getRoomId())
                .startDate(record.getStartDate())
                .beginTime(record.getBeginTime())
                .stopDate(record.getStopDate())
                .endTime(record.getEndTime())
                .cost(record.getCost())
                .status(record.getStatus())
                .needRecord(record.getNeedRecord())
                .build();

    }

    public static LivingRoomLiveRecord buildEntityFromRoom(LivingRoom room, Integer needRecord, LiveSourceEnum liveSource) {
        return LivingRoomLiveRecord.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .startDate(LocalDate.now())
                .beginTime(LocalDateTime.now())
                .status(LiveRecordStatusEnum.living.getCode())
                .needRecord(Objects.isNull(needRecord) ? BoolEnum.FALSE.getCode() : needRecord)
                .liveSource(liveSource.getCode())
                .build();
    }

    public static LivingStatusVo buildLivingStatus(Optional<Long> o) {
        return LivingStatusVo.builder()
                .status(o.isPresent() ? LiveRecordStatusEnum.living : LiveRecordStatusEnum.lived)
                .recordId(o.orElse(null))
                .build();
    }
}
