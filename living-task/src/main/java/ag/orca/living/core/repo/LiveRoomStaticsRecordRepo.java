package ag.orca.living.core.repo;

import ag.orca.living.core.convert.LivingRoomStaticsRecordConvert;
import ag.orca.living.core.dao.live.LivingRoomLiveRecordMapper;
import ag.orca.living.core.dao.order.OrderInfoMapper;
import ag.orca.living.core.dao.overview.LivingRoomStaticsRecordMapper;
import ag.orca.living.core.dao.share.LivingShareUserViewRecordMapper;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.overview.LivingRoomStaticsRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.vo.stats.LivingRoomOrderCountStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomShareUserStaticsVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Repository
public class LiveRoomStaticsRecordRepo {

    @Resource
    LivingRoomLiveRecordMapper roomLiveRecordMapper;

    @Resource
    LivingShareUserViewRecordMapper shareUserViewRecordMapper;

    @Resource
    LivingRoomStaticsRecordMapper roomStaticsRecordMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;


    public void processRoomStaticsRecord(LocalDateTime trigger, LivingRoom room) {
        LocalDate r = trigger.toLocalDate();
        LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(room.getStatus());
        if (status != LiveRecordStatusEnum.living) {
            return;
        }
        //可能跨天的
        LivingRoomLiveRecord liveRecord = roomLiveRecordMapper.findLatestLiveRecord(room.getId());
        LocalDateTime statTime = LocalDateTime.of(r, LocalTime.of(trigger.getHour(), trigger.getMinute(), 0));
        LivingRoomStaticsRecord record = LivingRoomStaticsRecordConvert.buildEntity(statTime, room, liveRecord.getId());
        LivingRoomShareUserStaticsVo staticsVo = shareUserViewRecordMapper.roomShareUserStatics(room.getId(), r, r);
        if (Objects.nonNull(staticsVo)) {
            record.setViewPageNum(staticsVo.getViewPageNum());
            record.setOnlineNum(staticsVo.getOnlineNum());
            record.setOfflineNum(staticsVo.getOfflineNum());
            record.setSenderNum(staticsVo.getSenderNum());
            record.setGiftNum(staticsVo.getGiftNum());
            record.setMsgNum(staticsVo.getMsgNum());
        }
        LivingRoomOrderCountStaticsVo orderStaticsVo = orderInfoMapper.roomOrderCountStatics(room.getId(), r, r);
        if (Objects.nonNull(orderStaticsVo)) {
            record.setOrderNum(orderStaticsVo.getOrderNum());
            record.setCancelOrderNum(orderStaticsVo.getCancelOrderNum());
            record.setPayedOrderNum(orderStaticsVo.getPayedOrderNum());
            record.setWaitOrderNum(orderStaticsVo.getWaitOrderNum());
            record.setTimeoutOrderNum(orderStaticsVo.getTimeoutOrderNum());
        }
        roomStaticsRecordMapper.insertOrUpdate(record);
    }
}
