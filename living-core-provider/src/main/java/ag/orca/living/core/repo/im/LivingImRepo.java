package ag.orca.living.core.repo.im;


import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.convert.ImMessageConvert;
import ag.orca.living.core.convert.MsgEventConvert;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.enums.MsgTypeEnum;
import ag.orca.living.core.event.RollbackMsgEvent;
import ag.orca.living.core.event.SendReplyMsgEvent;
import ag.orca.living.core.mongo.ImMessageRecord;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.org.OrgRobotRepo;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.pulsar.client.api.Producer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static ag.orca.living.common.MongoConst.IM_MESSAGE;

@Slf4j
@Repository
public class LivingImRepo {

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    Producer<SendReplyMsgEvent> sendReplyMsgEventProducer;

    @Resource
    Producer<RollbackMsgEvent> rollbackMsgEventProducer;

    @Resource
    OrgRobotRepo orgRobotRepo;

    @DubboReference
    LivingSeqService seqService;

    public void sendMsg(Long roomId, String data, String replyData, MsgTypeEnum msgType, AuthInfo authInfo) {
        Long ts = System.currentTimeMillis();
        String uuid = seqService.uuid();
        SendReplyMsgEvent event = MsgEventConvert.roToSendReplyMsgEvent(roomId, uuid, data, replyData, authInfo, ts, msgType);
        // 直接生成新的消息记录插入进 消息表
        ImMessageRecord record = ImMessageConvert.sendReplyMsgEventToImMessageRecord(ts, uuid, event, msgType);
        Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
        roomRecord.ifPresent(recordId -> mongoTemplate.save(record, IM_MESSAGE + roomId + "_" + recordId));
        // 消息发送出去
        sendReplyMsgEventProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[操作发送(回复)消息] roomId: {}, 操作人: {}-{}, msg: {}", roomId, authInfo.getUid(), authInfo.getNickname(), JsonUtil.beanToJson(event)));

    }

    public void rollbackMsg(Long roomId, String msgUid, AuthInfo authInfo) {
        Long ts = System.currentTimeMillis();
        RollbackMsgEvent event = MsgEventConvert.roToRollbackMsgEvent(roomId, msgUid, authInfo, ts);
        // 直接删除该记录消息
        Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
        roomRecord.ifPresent(recordId -> {
            Query query = Query.query(Criteria.where("msgUid").is(msgUid));
            mongoTemplate.remove(query, IM_MESSAGE + roomId + "_" + recordId);
        });
        // 消息发出去用于撤回
        // 消息发送出去
        rollbackMsgEventProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[操作撤回消息] roomId: {}, 操作人: {}-{}, msg: {}", roomId, authInfo.getUid(), authInfo.getNickname(), JsonUtil.beanToJson(event)));

    }

    public void robotSendMsg(Long roomId, Long robotId, String data, String replyData) {
        Long ts = System.currentTimeMillis();
        String uuid = seqService.uuid();
        Optional<OrgRobot> optional = orgRobotRepo.findById(robotId);
        optional.ifPresent(robot -> {
            SendReplyMsgEvent event = MsgEventConvert.robotToSendReplyMsgEvent(roomId, uuid, data, replyData, ts, robot);
            // 直接生成新的消息记录插入进 消息表
            ImMessageRecord record = ImMessageConvert.robotSendReplyMsgEventToImMessageRecord(ts, uuid, event);
            Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
            roomRecord.ifPresent(recordId -> mongoTemplate.save(record, IM_MESSAGE + roomId + "_" + recordId));
            // 消息发送出去
            sendReplyMsgEventProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[操作机器人(回复)消息] roomId: {}, 操作人: {}-{}, msg: {}", roomId, robot.getId(), robot.getNickname(), JsonUtil.beanToJson(event)));

        });

    }

}
