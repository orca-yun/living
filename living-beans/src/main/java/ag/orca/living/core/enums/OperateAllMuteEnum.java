package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperateAllMuteEnum {
    /**
     * 开启
     */
    on(1),

    /**
     * 关闭
     */
    off(0);

    private final int code;

    public static OperateAllMuteEnum ofCode(int code) {
        return Arrays.stream(OperateAllMuteEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("操作类型无法映射"));

    }
}
