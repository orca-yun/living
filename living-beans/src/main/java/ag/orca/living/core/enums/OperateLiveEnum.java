package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperateLiveEnum {

    /**
     * 开启直播
     */
    start(1),

    /**
     * 关闭直播
     */
    stop(2);

    private final int code;

    public static OperateLiveEnum ofCode(int code) {
        return Arrays.stream(OperateLiveEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("操作类型无法映射"));

    }
}
