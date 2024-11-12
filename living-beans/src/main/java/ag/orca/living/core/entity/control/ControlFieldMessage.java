package ag.orca.living.core.entity.control;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 场控消息
 *
 * @TableName t_field_control_message
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlFieldMessage extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 机器人ID
     */
    private Long robotId;

    /**
     * 机器人昵称
     */
    private String robotNickname;

    /**
     * 机器人头像
     */
    private String robotHeadIco;

    /**
     * 场控类别, 1: 氛围场控
     */
    private Integer controlType;

    /**
     * 场控ID
     */
    private Long controlId;

    /**
     * 消息类别, 1: 文本, 2: 礼物
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息触发时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime triggerTime;

    /**
     * 发送状态, 0: 未发送, 1: 已发送
     */
    private Integer status;

}