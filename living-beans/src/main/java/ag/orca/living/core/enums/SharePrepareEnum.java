package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SharePrepareEnum {

    /**
     * pass
     */
    PASS(1),

    /**
     * 过期
     */
    TOKEN_EXPIRE(2),

    /**
     * 非法
     */
    TOKEN_ILLEGAL(3),

    /**
     * 在黑名单中
     */
    IN_BLACKLIST(4);

    private final int code;

    public static SharePrepareEnum ofCode(int code) {
        return Arrays.stream(SharePrepareEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("TOKEN校验无法映射"));
    }
}
