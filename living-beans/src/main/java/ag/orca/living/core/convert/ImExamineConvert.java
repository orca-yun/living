package ag.orca.living.core.convert;

import ag.orca.living.core.mongo.ImExamineRecord;
import ag.orca.living.core.vo.examine.ImExamineRecordVo;

import java.util.Objects;

public class ImExamineConvert {

    public static ImExamineRecordVo recordToVo(ImExamineRecord record) {
        return Objects.isNull(record) ? null
                : ImExamineRecordVo.builder()
                .id(record.getId())
                .uid(record.getUid())
                .nickname(record.getNickname())
                .ts(record.getTs())
                .msgType(record.getMsgType())
                .msgUid(record.getMsgUid())
                .senderName(record.getSenderName())
                .senderUid(record.getSenderUid())
                .senderType(record.getSenderType())
                .senderHeadIco(record.getSenderHeadIco())
                .data(record.getData())
                .quotaData(record.getQuotaData())
                .examineStatus(record.getExamineStatus())
                .cts(record.getCts())
                .createTime(record.getCreateTime())
                .build();
    }
}
