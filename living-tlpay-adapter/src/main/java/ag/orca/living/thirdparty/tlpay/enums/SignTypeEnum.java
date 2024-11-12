package ag.orca.living.thirdparty.tlpay.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * 签名类型枚举
 * </p>
 */
@AllArgsConstructor
@Getter
public enum SignTypeEnum {
    MD5("MD5"),
    SM2("SM2"),
    RSA("RSA");

    private final String code;

    public static SignTypeEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(s -> s.code.equals(code)).findFirst()
                .orElseThrow(() -> OrcaException.error("错误的SignType：" + code));
    }
}
