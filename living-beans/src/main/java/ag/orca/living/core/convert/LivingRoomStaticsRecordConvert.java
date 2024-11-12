package ag.orca.living.core.convert;

import ag.orca.living.core.entity.overview.LivingRoomStaticsRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.vo.overview.LivingRoomStaticsRecordVo;

import java.time.LocalDateTime;
import java.util.Objects;

public class LivingRoomStaticsRecordConvert {

    public static LivingRoomStaticsRecordVo entityToVo(LivingRoomStaticsRecord record) {
        return Objects.isNull(record) ? null :
                LivingRoomStaticsRecordVo.builder()
                        .id(record.getId())
                        .statTime(record.getStatTime())
                        .orgId(record.getOrgId())
                        .roomId(record.getRoomId())
                        .roomRecordId(record.getRoomRecordId())
                        .viewPageNum(record.getViewPageNum())
                        .onlineNum(record.getOnlineNum())
                        .offlineNum(record.getOfflineNum())
                        .senderNum(record.getSenderNum())
                        .orderNum(record.getOrderNum())
                        .giftNum(record.getGiftNum())
                        .msgNum(record.getMsgNum())
                        .cancelOrderNum(record.getCancelOrderNum())
                        .waitOrderNum(record.getWaitOrderNum())
                        .payedOrderNum(record.getPayedOrderNum())
                        .timeoutOrderNum(record.getTimeoutOrderNum())
                        .build();
    }


    public static LivingRoomStaticsRecord buildEntity(LocalDateTime statTime,
                                                      LivingRoom room,
                                                      Long recordId) {
        return LivingRoomStaticsRecord.builder()
                .statTime(statTime)
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .roomRecordId(recordId)
                .viewPageNum(0L)
                .onlineNum(0L)
                .offlineNum(0L)
                .senderNum(0L)
                .orderNum(0L)
                .giftNum(0L)
                .msgNum(0L)
                .cancelOrderNum(0L)
                .waitOrderNum(0L)
                .payedOrderNum(0L)
                .timeoutOrderNum(0L)
                .build();
    }
}
