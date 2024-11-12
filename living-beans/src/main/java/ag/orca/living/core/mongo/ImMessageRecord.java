package ag.orca.living.core.mongo;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Document
public class ImMessageRecord extends BaseBean {
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


    @Indexed(background = true)
    private Long cts;

    @Indexed(background = true)
    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}
