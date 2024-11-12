package ag.orca.living.core.mongo;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@Document
public class ImExamineRecord extends BaseBean {
    @Id
    private String id;

    /**
     * 房间号
     */
    private Long roomId;

    /**
     * 指令人 用户ID
     */
    private String uid;

    /**
     * 用户名称
     */
    private String nickname;


    /**
     * 指令角色
     */
    private CrossRoleEnum role;


    /**
     * 时间戳
     */
    @Indexed(background = true)
    private long ts;

    /**
     * 唯一ID 指令的唯一ID
     */
    @Indexed(background = true)
    private String uuid;


    /**
     * 消息的类型
     */
    private MsgTypeEnum msgType;

    /**
     * 消息ID
     */
    @Indexed(background = true)
    private String msgUid;

    /**
     * 来源人
     */
    private String senderName;

    /**
     * 来源人用户ID
     */
    private String senderUid;


    /**
     * 发送人类型 // 0 主播 1 助理 2 用户 3 机器人【非必填】
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


    /**
     * UUID
     */
    private UUID sessionId;


    /**
     * 审批状态
     */
    private ExamineStatusEnum examineStatus;


    /**
     * 审核人
     */
    private String examineUid;


    /**
     * 审核人昵称
     */
    private String examineNickname;


    /**
     * 审核时间
     */
    private Long examineTs;


    @Indexed(background = true)
    private Long cts;

    @Indexed(background = true)
    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}
