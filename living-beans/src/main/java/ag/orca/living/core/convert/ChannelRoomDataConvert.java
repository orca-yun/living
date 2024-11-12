package ag.orca.living.core.convert;

import ag.orca.living.core.entity.channel.DayChannelData;
import ag.orca.living.core.entity.channel.DayLiveRoomChannelData;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import ag.orca.living.core.vo.channel.ChannelRoomDetailVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChannelRoomDataConvert {

    public static ChannelRoomDetailVo entityToVo(DayLiveRoomChannelData ro) {
        return Objects.isNull(ro) ? null : ChannelRoomDetailVo.builder()
                .id(ro.getId())
                .liveRecordId(ro.getLiveRecordId())
                .roomId(ro.getRoomId())
                .flowPeople(ro.getFlowPeople())
                .transactionAmount(ro.getTransactionAmount())
                .subCommission(ro.getSubCommission())
                .commissionRatio(ro.getCommissionRatio())
                .build();
    }

    public static ChannelDataDetailVo entityToChannelDate(DayChannelData ro) {
        return Objects.isNull(ro) ? null : ChannelDataDetailVo.builder()
                .channelId(ro.getChannelId())
                .channelName(ro.getChannelName())
                .transactionAmount(ro.getTransactionAmount())
                .subCommission(ro.getSubCommission())
                .commissionRatio(ro.getCommissionRatio())
                .build();
    }

    public static DayLiveRoomChannelData voToEntity(ChannelDataDetailVo ro) {
        return Objects.isNull(ro) ? null : DayLiveRoomChannelData.builder()
                .channelId(ro.getChannelId())
                .roomId(ro.getId())
                .channelName(ro.getChannelName())
                .flowPeople(ro.getFlowPeople())
                .transactionAmount(ro.getTransactionAmount())
                .subCommission(ro.getSubCommission())
                .createTime(LocalDateTime.now())
                .day(LocalDate.now())
                .build();
    }
}
