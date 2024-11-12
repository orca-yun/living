package ag.orca.living.core.convert;

import ag.orca.living.core.entity.share.UserChannelRelation;
import ag.orca.living.core.entity.share.UserInfo;

import java.time.LocalDate;
import java.util.Optional;

public class UserChannelRelationConvert {

    public static UserChannelRelation buildEntity(Long channelId,
                                                  Long orgId,
                                                  Long roomId,
                                                  UserInfo userInfo, Optional<Long> recordOptional) {
        return UserChannelRelation.builder()
                .orgId(orgId)
                .channelId(channelId)
                .roomId(roomId)
                .liveRecordId(recordOptional.orElse(null))
                .userId(userInfo.getId())
                .openId(userInfo.getOpenId())
                .bindDate(LocalDate.now())
                .build();
    }
}
