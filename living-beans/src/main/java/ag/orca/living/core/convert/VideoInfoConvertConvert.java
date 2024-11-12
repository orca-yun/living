package ag.orca.living.core.convert;

import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.event.VideoInfoConvertEvent;

public class VideoInfoConvertConvert {
    public static VideoInfoConvertEvent livingMediaLibToVideoConvertEvent(LivingMediaLib lib) {

        return VideoInfoConvertEvent.builder()
                .mediaId(lib.getId())
                .bucket(lib.getBucket())
                .pathName(lib.getPathName())
                .build();

    }
}
