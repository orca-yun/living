package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 观看权限: 1 无密码 2 密码 3 付费
 */
@AllArgsConstructor
@Getter
public enum PermissionTypeEnum {

    /**
     * 1 无密码
     */
    NO_PWD(1),

    /**
     * 密码
     */
    NEED_PWD(2),

    /**
     * 付费
     */
    NEED_PAY(3);

    private final int code;

    public static PermissionTypeEnum ofCode(int code) {
        return Arrays.stream(PermissionTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("观看权限无法映射"));

    }
}
