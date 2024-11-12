package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ActionTypeEnum {
    /**
     * 初始化
     */
    ONLINE(1),

    /**
     * 正常
     */
    OFFLINE(2);

    private final int code;


    public static ActionTypeEnum ofCode(int code) {
        return Arrays.stream(ActionTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("上下线动作枚举无法映射"));
    }

}
