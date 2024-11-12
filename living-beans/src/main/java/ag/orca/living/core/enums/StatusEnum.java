package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StatusEnum {


    INVALID(0),

    /**
     * 映射出 0
     */
    NORMAL(1);

    private final int code;


    public static StatusEnum ofCode(int code) {
        return Arrays.stream(StatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("业务状态无法映射"));
    }


}
