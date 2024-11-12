package ag.orca.living.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息发送状态枚举, 0: 未发送, 1: 已发送
 *
 * 
 * @date 2024-04-13
 */
@Getter
@AllArgsConstructor
public enum ControlMessageStatusEnum {

    /**
     * 未发送
     */
    NOT_SENT(0),

    /**
     * 已发送
     */
    SENT(1);

    private final Integer code;
}
