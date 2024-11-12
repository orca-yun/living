package ag.orca.living.core.enums;


import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付类型
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    /**
     * 1 跳转支付
     */
    JUMP(1),

    /**
     * 2 在线支付
     */
    ONLINE(2),

    /**
     * 3 小程序支付
     */
    MINI(3),

    /**
     * 4 扫码支付
     */
    QRCODE(4);


    private final int code;

    public static PayTypeEnum ofCode(int code) {
        return Arrays.stream(PayTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("支付类型无法映射"));

    }


}
