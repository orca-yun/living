package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum PcLayoutEnum {

    /**
     * 2 二分屏
     */
    TWO_LAYOUT("2"),

    /**
     * 3 三分屏
     */
    THREE_LAYOUT("3");

    private final String code;

    public static PcLayoutEnum ofCode(String code) {
        return Arrays.stream(PcLayoutEnum.values())
                .filter(s -> Objects.equals(s.code, code))
                .findAny().orElseThrow(() -> OrcaException.error("模板类型无法映射"));

    }
}
