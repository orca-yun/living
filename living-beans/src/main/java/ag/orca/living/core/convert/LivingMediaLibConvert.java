package ag.orca.living.core.convert;

import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.MediaLibTypeEnum;
import ag.orca.living.core.enums.VideoConvertStatusEnum;
import ag.orca.living.core.vo.media.LivingMediaLibInfoVo;
import ag.orca.living.core.vo.media.LivingMediaLibVo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LivingMediaLibConvert {


    public static LivingMediaLibVo entityToSimpleVo(LivingMediaLib s) {
        if (Objects.isNull(s)) {
            return null;
        }
        return LivingMediaLibVo.builder()
                .id(s.getId())
                .orgId(s.getOrgId())
                .name(s.getName())
                .fileName(s.getFileName())
                .bucket(s.getBucket())
                .pathName(s.getPathName())
                .duration(s.getDuration())
                .capacity(s.getCapacity())
                .image(s.getImage())
                .mediaType(s.getMediaType())
                .roomId(s.getRoomId())
                .roomRecordId(s.getRoomRecordId())
                .convertStatus(s.getConvertStatus())
                .build();
    }

    public static LivingMediaLibInfoVo entityToVo(LivingMediaLib s, List<LivingRoom> rooms, List<LivingRoomLiveRecord> records) {
        if (Objects.isNull(s)) {
            return null;
        }
        MediaLibTypeEnum type = MediaLibTypeEnum.ofCode(s.getMediaType());
        Optional<LivingRoom> roomOptional = MediaLibTypeEnum.MANUAL == type ?
                Optional.empty() :
                rooms.stream().filter(r -> r.getId().equals(s.getRoomId())).findFirst();
        Optional<LivingRoomLiveRecord> recordOptional = MediaLibTypeEnum.MANUAL == type ?
                Optional.empty() :
                records.stream().filter(r -> r.getId().equals(s.getRoomRecordId())).findFirst();
        return LivingMediaLibInfoVo.builder()
                .id(s.getId())
                .orgId(s.getOrgId())
                .name(s.getName())
                .fileName(s.getFileName())
                .bucket(s.getBucket())
                .pathName(s.getPathName())
                .duration(s.getDuration())
                .capacity(s.getCapacity())
                .image(s.getImage())
                .mediaType(s.getMediaType())
                .roomId(s.getRoomId())
                .roomRecordId(s.getRoomRecordId())
                .roomName(roomOptional.map(LivingRoom::getName).orElse(""))
                .startDate(recordOptional.map(LivingRoomLiveRecord::getStartDate).orElse(null))
                .beginTime(recordOptional.map(LivingRoomLiveRecord::getBeginTime).orElse(null))
                .stopDate(recordOptional.map(LivingRoomLiveRecord::getStopDate).orElse(null))
                .endTime(recordOptional.map(LivingRoomLiveRecord::getEndTime).orElse(null))
                .cost(recordOptional.map(LivingRoomLiveRecord::getCost).orElse(null))
                .convertStatus(s.getConvertStatus())
                .build();
    }

    public static LivingMediaLib buildEntity(Long orgId,
                                             String name,
                                             String fileName,
                                             String bucket,
                                             String key) {
        return LivingMediaLib.builder()
                .orgId(orgId)
                .name(name)
                .fileName(fileName)
                .bucket(bucket)
                .pathName(key)
                .mediaType(MediaLibTypeEnum.MANUAL.getCode())
                .convertStatus(VideoConvertStatusEnum.WAITING.getCode())
                .build();
    }
}
