package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 场控消息事件
 *
 * 
 * @date 2024-05-07
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlMessageEvent extends BaseBean {

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
    private LocalDateTime triggerTime;

}
