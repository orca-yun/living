package ag.orca.living.core.convert;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.event.ShareUserStatEvent;
import org.apache.commons.lang3.StringUtils;


public class ShareUserStatConvert {

    public static ShareUserStatEvent buildEvent(AuthInfo authInfo, Long roomRecordId,
                                                String ua) {
        return ShareUserStatEvent.builder()
                .roomId(authInfo.getRoomId())
                .channelId(authInfo.getChannelId())
                .uid(authInfo.getUid())
                .nickname(authInfo.getNickname())
                .headIco(authInfo.getHeadIco())
                .roomRecordId(roomRecordId)
                .timestamp(System.currentTimeMillis())
                .userAgent(StringUtils.isEmpty(ua) ? "" : ua)
                .build();
    }
}
