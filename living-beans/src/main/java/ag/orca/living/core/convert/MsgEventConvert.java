package ag.orca.living.core.convert;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.bo.BlacklistUserInfo;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import ag.orca.living.core.event.ImExamineEvent;
import ag.orca.living.core.event.RollbackMsgEvent;
import ag.orca.living.core.event.SendReplyMsgEvent;
import ag.orca.living.core.event.UserBlacklistEvent;
import ag.orca.living.core.mongo.ImExamineRecord;

public class MsgEventConvert {


    public static ImExamineEvent entityToImExamineEvent(ImExamineRecord item) {
        return ImExamineEvent.builder()
                .roomId(item.getRoomId())
                .sessionId(item.getSessionId())
                .ts(item.getTs())
                .uuid(item.getUuid())
                .msgType(item.getMsgType())
                .msgUid(item.getMsgUid())
                .senderName(item.getSenderName())
                .senderUid(item.getSenderUid())
                .senderType(item.getSenderType())
                .senderHeadIco(item.getSenderHeadIco())
                .data(item.getData())
                .quotaData(item.getQuotaData())
                .examineStatus(item.getExamineStatus())
                .build();
    }

    public static SendReplyMsgEvent roToSendReplyMsgEvent(Long roomId,
                                                          String msgUid,
                                                          String data,
                                                          String replyData,
                                                          AuthInfo authInfo,
                                                          Long ts, MsgTypeEnum msgType) {
        return SendReplyMsgEvent.builder()
                .roomId(roomId)
                .msgUid(msgUid)
                .msgType(msgType)
                .data(data)
                .replyData(replyData)
                .ts(ts)
                .senderUid(authInfo.getUid())
                .senderType(authInfo.getRole())
                .senderName(authInfo.getNickname())
                .senderHeadIco(authInfo.getHeadIco())
                .build();
    }

    public static SendReplyMsgEvent robotToSendReplyMsgEvent(Long roomId,
                                                             String msgUid,
                                                             String data,
                                                             String replyData,
                                                             Long ts,
                                                             OrgRobot robot) {
        return SendReplyMsgEvent.builder()
                .roomId(roomId)
                .msgUid(msgUid)
                .data(data)
                .replyData(replyData)
                .msgType(MsgTypeEnum.normal)
                .ts(ts)
                .senderUid(robot.getCode())
                .senderType(CrossRoleEnum.ROBOT.getCode())
                .senderName(robot.getNickname())
                .senderHeadIco(robot.getHeadIco())
                .build();
    }


    public static RollbackMsgEvent roToRollbackMsgEvent(Long roomId,
                                                        String msgUid,
                                                        AuthInfo authInfo,
                                                        Long ts) {
        return RollbackMsgEvent.builder()
                .roomId(roomId)
                .msgUid(msgUid)
                .ts(ts)
                .senderUid(authInfo.getUid())
                .senderType(authInfo.getRole())
                .senderName(authInfo.getNickname())
                .senderHeadIco(authInfo.getHeadIco())
                .build();
    }


    public static UserBlacklistEvent entityToUserBlacklistEvent(BlacklistUserInfo info, boolean join) {
        return UserBlacklistEvent.builder()
                .uid(info.getUid())
                .roomId(info.getRoomId())
                .join(join)
                .build();
    }
}
