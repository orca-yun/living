package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum MobileLayoutEnum {

    /**
     * 1 全屏 竖屏
     */
    FULL_SCREEN("1"),

    /**
     * 2 二分屏
     */
    TWO_LAYOUT("2"),

    /**
     * 3 三分屏
     */
    THREE_LAYOUT("3");

    private final String code;

    public static MobileLayoutEnum ofCode(String code) {
        return Arrays.stream(MobileLayoutEnum.values())
                .filter(s -> Objects.equals(s.code, code))
                .findAny().orElseThrow(() -> OrcaException.error("模板类型无法映射"));

    }
}
