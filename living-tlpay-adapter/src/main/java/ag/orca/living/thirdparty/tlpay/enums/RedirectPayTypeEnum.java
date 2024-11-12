package ag.orca.living.thirdparty.tlpay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * pc和移动端支付场景枚举
 * </p>
 */

@AllArgsConstructor
@Getter
public enum RedirectPayTypeEnum {
    PHONE_TYPE(1, "手机端支付"),
    PC_TYPE(2, "pc端支付"),
    ;
    private final int code;
    private final String msg;

    public static RedirectPayTypeEnum fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code).findFirst().orElse(null);
    }
}
