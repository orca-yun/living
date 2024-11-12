package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CrossRoleEnum {

    /**
     * 主播
     */
    ANCHOR(0),

    /**
     * 助理
     */
    ASSISTANT(1),

    /**
     * 用户
     */
    SHARE(2),

    /**
     * 机器人
     */
    ROBOT(3);


    private final int code;

    public static CrossRoleEnum ofCode(int code) {
        return Arrays.stream(CrossRoleEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("用户类型无法映射"));

    }

}
