package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ShowStyleEnum {

    MIN(1),

    MAX(2);

    private final int code;

    public static ShowStyleEnum ofCode(int code) {
        return Arrays.stream(ShowStyleEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("推荐弹框无法映射"));

    }
}
