package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CommissionMethodEnum {
    /**
     * 自动分佣
     */
    AUTO(1),

    /**
     * 手动分佣
     */
    HANDLER(2);


    private final int code;


    public static CommissionMethodEnum ofCode(int code) {
        return Arrays.stream(CommissionMethodEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("分佣方式无法映射"));
    }
}
