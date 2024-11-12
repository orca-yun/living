package ag.orca.living.core.enums;


import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LivingTypeEnum {

    /**
     * 1 标准延迟(标准直播),
     */
    STANDARD(1),


    /**
     * 2 超低延迟(快直播)
     */
    FAST(2);

    private final int code;

    public static LivingTypeEnum ofCode(int code) {
        return Arrays.stream(LivingTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("直播类型无法映射"));

    }


}
