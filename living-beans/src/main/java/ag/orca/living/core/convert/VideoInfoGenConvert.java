package ag.orca.living.core.convert;

import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.enums.VideoImageTypeEnum;
import ag.orca.living.core.event.VideoInfoGenEvent;

public class VideoInfoGenConvert {

    public static VideoInfoGenEvent livingMediaLibToVideoGenEvent(LivingMediaLib lib, Integer start) {

        return VideoInfoGenEvent.builder()
                .mediaId(lib.getId())
                .bucket(lib.getBucket())
                .pathName(lib.getPathName())
                .start(start)
                .type(VideoImageTypeEnum.png.getCode())
                .build();

    }
}
