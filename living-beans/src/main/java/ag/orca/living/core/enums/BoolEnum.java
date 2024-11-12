package ag.orca.living.core.enums;


import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BoolEnum {

    /**
     * false 0
     */
    FALSE(0, "否"),

    /**
     * true 1
     */
    TRUE(1, "是");

    private final int code;

    private final String describe;

    public static BoolEnum ofCode(int code) {
        return Arrays.stream(BoolEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("枚举CODE无法映射"));
    }

}
