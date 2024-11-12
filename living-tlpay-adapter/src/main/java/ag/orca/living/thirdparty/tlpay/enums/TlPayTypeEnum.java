package ag.orca.living.thirdparty.tlpay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * 通联PayType枚举
 * </p>
 */
@Getter
@AllArgsConstructor
public enum TlPayTypeEnum {
    WX_SCAN("W01", "微信扫码支付"),
    WX_JS("W02", "微信JS支付"),
    WX_APP("W06", "微信小程序支付"),
    ;
    private final String code;
    private final String msg;

    public static TlPayTypeEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(s -> s.code.equals(code)).findFirst().orElse(null);
    }

}
