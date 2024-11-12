package ag.orca.living.core.convert;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.share.LivingShareUserViewRecord;
import ag.orca.living.core.enums.ActionTypeEnum;
import ag.orca.living.core.enums.UserSendStatTypeEnum;
import ag.orca.living.core.event.ShareUserActionEvent;
import ag.orca.living.core.event.ShareUserStatEvent;
import ag.orca.living.core.event.UserSendStatEvent;
import ag.orca.living.core.vo.share.LivingShareUserViewRecordVo;
import ag.orca.living.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LivingShareUserViewRecordConvert {

    public static LivingShareUserViewRecord actionEventToRecord(ShareUserActionEvent action) {
        //上报时间
        Long timestamp = action.getTimestamp();
        LocalDateTime eventTime = DateUtil.dateTimeOfTimestamp(timestamp);
        ActionTypeEnum actionType = ActionTypeEnum.ofCode(action.getActionType());
        return LivingShareUserViewRecord.builder()
                .viewDate(eventTime.toLocalDate())
                .orgId(action.getOrgId())
                .roomId(action.getRoomId())
                .channelId(action.getChannelId())
                .uid(action.getUid())
                .nickname(action.getNickname())
                .headIco(action.getHeadIco())
                .eventTs(timestamp)
                .eventTime(eventTime)
                .status(action.getActionType())
                .tsOnline(actionType == ActionTypeEnum.ONLINE ? timestamp : null)
                .tsOffline(actionType == ActionTypeEnum.OFFLINE ? timestamp : null)
                .onlineTime(actionType == ActionTypeEnum.ONLINE ? eventTime : null)
                .offlineTime(actionType == ActionTypeEnum.OFFLINE ? eventTime : null)
                .onlineTimes(actionType == ActionTypeEnum.ONLINE ? 1 : null)
                .build();
    }

    public static LivingShareUserViewRecord reportEventToRecord(ShareUserStatEvent event) {
        //上报时间
        Long timestamp = event.getTimestamp();
        LocalDateTime eventTime = DateUtil.dateTimeOfTimestamp(timestamp);
        return LivingShareUserViewRecord.builder()
                .viewDate(eventTime.toLocalDate())
                .orgId(event.getOrgId())
                .roomId(event.getRoomId())
                .channelId(event.getChannelId())
                .uid(event.getUid())
                .nickname(event.getNickname())
                .headIco(event.getHeadIco())
                .roomRecordId(event.getRoomRecordId())
                .userAgent(event.getUserAgent())
                .eventTs(timestamp)
                .eventTime(eventTime)
                .viewDuration(5L)
                .build();
    }

    public static LivingShareUserViewRecord userSendEventToRecord(UserSendStatEvent event) {
        LivingShareUserViewRecord record = LivingShareUserViewRecord.builder()
                .viewDate(event.getViewDate())
                .orgId(event.getOrgId())
                .roomId(event.getRoomId())
                .channelId(event.getChannelId())
                .uid(event.getUid())
                .build();
        UserSendStatTypeEnum type = UserSendStatTypeEnum.ofCode(event.getType());
        if (type == UserSendStatTypeEnum.gift) {
            record.setGiftNum(1L);
        }
        if (type == UserSendStatTypeEnum.msg) {
            record.setMsgNum(1L);
        }
        return record;
    }

    public static LivingShareUserViewRecordVo entityToVo(LivingShareUserViewRecord record, List<ChannelInfo> channelInfos) {
        if (Objects.isNull(record)) {
            return null;
        }
        Optional<ChannelInfo> optional = CommonConvert.filter(channelInfos, s -> s.getChannelId().equals(record.getChannelId())).stream().findFirst();
        return LivingShareUserViewRecordVo.builder()
                .viewDate(record.getViewDate())
                .roomId(record.getRoomId())
                .channelId(record.getChannelId())
                .uid(record.getUid())
                .nickname(record.getNickname())
                .headIco(record.getHeadIco())
                .viewDuration(record.getViewDuration())
                .giftNum(record.getGiftNum())
                .msgNum(record.getMsgNum())
                .userAgent(record.getUserAgent())
                .tsOnline(record.getTsOnline())
                .tsOffline(record.getTsOffline())
                .onlineTime(record.getOnlineTime())
                .offlineTime(record.getOfflineTime())
                .status(record.getStatus())
                .channelName(optional.map(ChannelInfo::getChannelName).orElse(""))
                .onlineTimes(record.getOnlineTimes())
                .build();
    }

}
