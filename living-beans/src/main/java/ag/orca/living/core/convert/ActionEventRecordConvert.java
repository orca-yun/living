package ag.orca.living.core.convert;

import ag.orca.living.core.event.GiftActionEvent;
import ag.orca.living.core.event.GoodsActionEvent;
import ag.orca.living.core.mongo.GiftActionEventRecord;
import ag.orca.living.core.mongo.GoodsActionEventRecord;

import java.time.LocalDateTime;

import static ag.orca.living.common.OrcaConst.ZONE_OFFSET;

public class ActionEventRecordConvert {

    public static GiftActionEventRecord buildGiftActionEventRecord(GiftActionEvent event) {
        GiftActionEventRecord record = new GiftActionEventRecord();
        record.setItems(event.getItems());
        record.setRoomId(event.getRoomId());

        LocalDateTime now = LocalDateTime.now();
        record.setCts(now.toEpochSecond(ZONE_OFFSET));
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }


    public static GoodsActionEventRecord buildGoodsActionEventRecord(GoodsActionEvent event) {
        GoodsActionEventRecord record = new GoodsActionEventRecord();
        record.setRoomId(event.getRoomId());
        record.setRecommend(event.getRecommend());
        record.setItems(event.getItems());

        LocalDateTime now = LocalDateTime.now();
        record.setCts(now.toEpochSecond(ZONE_OFFSET));
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }
}
