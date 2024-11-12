package ag.orca.living.core.enums;


import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 扫码登录
     */
    LOGIN(0),

    /**
     * 缓存登录
     */
    CACHE(1);

    private final int code;

    public static LoginTypeEnum ofCode(int code) {
        return Arrays.stream(LoginTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("枚举CODE无法映射"));
    }

}
