package ag.orca.living.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 场控执行状态枚举, 0: 已停止, 1: 已启动
 *
 * 
 * 
 */
@Getter
@AllArgsConstructor
public enum ControlExecStatusEnum {

    /**
     * 已停止
     */
    STOPPED(0),

    /**
     * 已启动
     */
    STARTED(1);

    private final Integer code;
}
