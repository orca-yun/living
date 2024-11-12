package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 礼物类型
 */
@Getter
@AllArgsConstructor
public enum GiftTypeEnum {

    /**
     * 静态
     */
    STATIC(1),

    /**
     * 动态
     */
    DYNAMIC(2);


    private final int code;


    public static GiftTypeEnum ofCode(int code) {
        return Arrays.stream(GiftTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("礼物类型无法映射"));
    }
}
