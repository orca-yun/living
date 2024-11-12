package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ImExamineEvent extends BaseBean {


    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * sessionId
     */
    private UUID sessionId;

    /**
     * 时间戳
     */
    private long ts;

    /**
     * 唯一ID 指令的唯一ID
     */
    private String uuid;


    /**
     * 消息的类型
     */
    private MsgTypeEnum msgType;

    /**
     * 消息ID
     */
    private String msgUid;


    /**
     * 发送人
     */
    private String senderName;

    /**
     * 发送人ID
     */
    private String senderUid;

    /**
     * 发送人类型
     */
    private Integer senderType;

    /**
     * 发送人头像
     */
    private String senderHeadIco;

    /**
     * 消息内容
     */
    private String data;


    /**
     * 引用内容
     */
    private String quotaData;


    private ExamineStatusEnum examineStatus;


}
