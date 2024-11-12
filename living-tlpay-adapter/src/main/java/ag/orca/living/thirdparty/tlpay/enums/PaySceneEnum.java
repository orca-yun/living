package ag.orca.living.thirdparty.tlpay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * 支付场景枚举
 * </p>
 */
@AllArgsConstructor
@Getter
public enum PaySceneEnum {
    WX_APPLET(1, "小程序微信支付", TlPayTypeEnum.WX_APP),
    WX_BROWSER(2, "H5-微信浏览器支付", TlPayTypeEnum.WX_SCAN),
    WX_SCAN(3, "H5-浏览器微信扫码支付", TlPayTypeEnum.WX_SCAN),
    WX_JS(4, "APP-微信JS支付", TlPayTypeEnum.WX_JS),
    ;
    private final int code;

    private final String msg;

    private final TlPayTypeEnum payType;

    public static PaySceneEnum fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code).findFirst().orElse(null);
    }

}
