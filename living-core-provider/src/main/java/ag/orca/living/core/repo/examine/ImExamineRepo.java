package ag.orca.living.core.repo.examine;


import ag.orca.living.core.convert.ImMessageConvert;
import ag.orca.living.core.convert.MsgEventConvert;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.event.ImExamineEvent;
import ag.orca.living.core.mongo.ImExamineRecord;
import ag.orca.living.core.mongo.ImMessageRecord;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.api.Producer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.MongoConst.IM_EXAMINE;
import static ag.orca.living.common.MongoConst.IM_MESSAGE;

@Slf4j
@Repository
public class ImExamineRepo {

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    Producer<ImExamineEvent> imExamineProducer;


    public List<ImExamineRecord> findLatestExamineMsgList(Long roomId, int size, ExamineStatusEnum status, String keyword) {
        Criteria criteria = Criteria.where("roomId").is(roomId).and("examineStatus").is(status);
        if (StringUtils.isNotBlank(keyword)) {
            criteria.and("data").regex(keyword);
        }
        Query query = Query.query(criteria);
        query.with(Sort.by("cts").descending());
        query.limit(size);
        return mongoTemplate.find(query, ImExamineRecord.class, IM_EXAMINE + roomId);
    }

    public void operateImExamineRecord(Long roomId,
                                       String id,
                                       ExamineStatusEnum status,
                                       String operateUid,
                                       String operateNickname) {
        //找到那条消息
        String coll = IM_EXAMINE + roomId;
        ImExamineRecord record = mongoTemplate.findById(id, ImExamineRecord.class, coll);
        if (Objects.isNull(record)) {
            return;
        }
        if (record.getExamineStatus() == ExamineStatusEnum.init) {
            record.setExamineUid(operateUid);
            record.setExamineNickname(operateNickname);
            record.setExamineStatus(status);
            Long examineTs = System.currentTimeMillis();
            record.setExamineTs(examineTs);
            //将消息状态变更成审核过
            Query query = Query.query(Criteria.where("_id").is(id));
            Update update = new Update();
            update.set("examineStatus", status);
            update.set("examineUid", operateUid);
            update.set("examineNickname", operateNickname);
            update.set("examineTs", examineTs);
            mongoTemplate.updateFirst(query, update, ImExamineRecord.class, coll);


            Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
            roomRecord.ifPresent(recordId -> {
                ImMessageRecord imMessageRecord = ImMessageConvert.examineMsgToImMessageRecord(record);
                mongoTemplate.save(imMessageRecord, IM_MESSAGE + roomId + "_" + recordId);
            });

            //审核通过的要进行转发【发MQ】
            ImExamineEvent event = MsgEventConvert.entityToImExamineEvent(record);
            imExamineProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[操作消息审核] roomId: {}, 操作人: {}-{}, msg: {}", roomId, operateUid, operateNickname, JsonUtil.beanToJson(event)));

        }


    }

    public void deleteByRoomIdAndUid(Long roomId, String uid) {
        Query query = Query.query(Criteria.where("roomId").is(roomId).and("uid").is(uid));
        mongoTemplate.remove(query, ImExamineRecord.class, IM_EXAMINE + roomId);
    }


    public void cleanExamineMsg(Long roomId) {
        Query query = Query.query(Criteria.where("roomId").is(roomId));
        mongoTemplate.remove(query, ImExamineRecord.class, IM_EXAMINE + roomId);
    }
}
