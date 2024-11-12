package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WxLoginTypeEnum {

    /**
     * PC端登录
     */
    PC(1),

    /**
     * 手机端登录
     */
    PHONE(2);


    private final int code;

    public static WxLoginTypeEnum ofCode(int code) {
        return Arrays.stream(WxLoginTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("用户登录方式无法映射"));

    }

}
