package ag.orca.living.core.entity.control;

import ag.orca.living.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 场控剧本
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlScript extends BaseEntity {

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
     * 机器人编号
     */
    private String robotCode;

    /**
     * 机器人昵称
     */
    private String robotNickname;

    /**
     * 机器人头像
     */
    private String robotHeadIco;

    /**
     * 消息类别, 1: 文本, 2: 礼物
     */
    private Integer messageType;

    /**
     * 发送内容
     */
    private String content;

}