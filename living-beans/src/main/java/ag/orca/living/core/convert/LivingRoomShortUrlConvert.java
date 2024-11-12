package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomShortUrl;

public class LivingRoomShortUrlConvert {

    public static LivingRoomShortUrl buildRoomShortUrl(LivingRoom room, Long channelId, String randomStr) {
        return LivingRoomShortUrl.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .channelId(channelId)
                .randomStr(randomStr)
                .build();
    }
}
