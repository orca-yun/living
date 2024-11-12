package ag.orca.living.core.convert;

import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import ag.orca.living.core.event.SendReplyMsgEvent;
import ag.orca.living.core.mongo.ImExamineRecord;
import ag.orca.living.core.mongo.ImMessageRecord;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static ag.orca.living.common.OrcaConst.ZONE_OFFSET;

public class ImMessageConvert {
    public static ImMessageRecord sendReplyMsgEventToImMessageRecord(Long ts,
                                                                     String msgUid,
                                                                     SendReplyMsgEvent event,
                                                                     MsgTypeEnum msgType) {

        ImMessageRecord record = new ImMessageRecord();
        record.setRoomId(event.getRoomId());
        record.setUid(event.getSenderUid());
        record.setNickname(event.getSenderName());
        record.setRole(CrossRoleEnum.ofCode(event.getSenderType()));
        record.setTs(ts);
        record.setUuid(msgUid);
        msgType = StringUtils.isNotBlank(event.getReplyData()) ? MsgTypeEnum.reply : msgType;
        record.setMsgType(msgType);
        record.setMsgUid(msgUid);
        record.setSenderName(event.getSenderName());
        record.setSenderUid(event.getSenderUid());
        record.setSenderType(event.getSenderType());
        record.setSenderHeadIco(event.getSenderHeadIco());
        if (msgType == MsgTypeEnum.reply) {
            record.setData(event.getReplyData());
            record.setQuotaData(event.getData());
        } else {
            record.setData(event.getData());
        }
        LocalDateTime now = LocalDateTime.now();
        record.setCts(now.toEpochSecond(ZONE_OFFSET));
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    public static ImMessageRecord robotSendReplyMsgEventToImMessageRecord(Long ts, String uuid, SendReplyMsgEvent event) {
        ImMessageRecord record = new ImMessageRecord();
        record.setRoomId(event.getRoomId());
        record.setUid(event.getSenderUid());
        record.setNickname(event.getSenderName());
        record.setRole(CrossRoleEnum.ROBOT);
        record.setTs(ts);
        record.setUuid(uuid);
        MsgTypeEnum msgType = StringUtils.isNotBlank(event.getReplyData()) ? MsgTypeEnum.reply : MsgTypeEnum.normal;
        record.setMsgType(msgType);
        record.setMsgUid(uuid);
        record.setSenderName(event.getSenderName());
        record.setSenderUid(event.getSenderUid());
        record.setSenderType(event.getSenderType());
        record.setSenderHeadIco(event.getSenderHeadIco());
        if (msgType == MsgTypeEnum.reply) {
            record.setData(event.getReplyData());
            record.setQuotaData(event.getData());
        } else {
            record.setData(event.getData());
        }

        LocalDateTime now = LocalDateTime.now();
        record.setCts(now.toEpochSecond(ZONE_OFFSET));
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }

    public static ImMessageRecord examineMsgToImMessageRecord(ImExamineRecord r) {
        ImMessageRecord record = new ImMessageRecord();
        record.setRoomId(r.getRoomId());
        record.setUid(r.getUid());
        record.setNickname(r.getNickname());
        record.setRole(r.getRole());
        record.setTs(r.getTs());
        record.setUuid(r.getUuid());
        record.setMsgType(r.getMsgType());
        record.setMsgUid(r.getMsgUid());
        record.setSenderName(r.getSenderName());
        record.setSenderUid(r.getSenderUid());
        record.setSenderType(r.getSenderType());
        record.setSenderHeadIco(r.getSenderHeadIco());

        record.setData(r.getData());
        record.setQuotaData(r.getQuotaData());

        LocalDateTime now = LocalDateTime.now();
        record.setCts(now.toEpochSecond(ZONE_OFFSET));
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return record;
    }
}
