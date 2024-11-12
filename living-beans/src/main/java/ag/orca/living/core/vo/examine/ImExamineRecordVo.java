package ag.orca.living.core.vo.examine;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.enums.MsgTypeEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ImExamineRecordVo extends BaseBean {

    @Schema(name = "id", description = "消息ID")
    private String id;

    /**
     * 指令人 用户ID
     */
    @Schema(name = "uid", description = "指令人 用户ID")
    private String uid;

    /**
     * 用户名称
     */
    @Schema(name = "nickname", description = "用户名称")
    private String nickname;

    @Schema(name = "ts", description = "ts")
    private long ts;

    /**
     * 消息的类型
     */
    @Schema(name = "msgType", description = "消息的类型: cancel, reply, normal, img")
    private MsgTypeEnum msgType;

    /**
     * 消息ID
     */
    @Schema(name = "msgUid", description = "消息ID")
    private String msgUid;


    /**
     * 来源人
     */
    @Schema(name = "senderName", description = "来源人")
    private String senderName;

    /**
     * 来源人用户ID
     */
    @Schema(name = "senderUid", description = "来源人用户ID")
    private String senderUid;

    /**
     * 发送人类型 // 0 主播 1 助理 2 用户 3 机器人【非必填】
     */
    @Schema(name = "senderType", description = "发送人类型 0 主播 1 助理 2 用户 3 机器人")
    private Integer senderType;

    /**
     * 发送人头像
     */
    @Schema(name = "senderHeadIco", description = "发送人头像")
    private String senderHeadIco;


    /**
     * 消息内容
     */
    @Schema(name = "data", description = "消息内容")
    private String data;


    /**
     * 引用内容
     */
    @Schema(name = "quotaData", description = "引用内容")
    private String quotaData;


    /**
     * 审批状态
     */
    @Schema(name = "examineStatus", description = "审批状态 init, approve, disapprove")
    private ExamineStatusEnum examineStatus;

    @Schema(name = "cts", description = "入库时间戳")
    private Long cts;

    @Schema(name = "createTime", description = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
