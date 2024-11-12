package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ChannelQueryFlowTypeEnum {

    /**
     * 正序查询
     */
    asc(0),
    /**
     * 倒叙查询
     */
    desc(1);

    private final int code;

    public static ChannelQueryFlowTypeEnum ofCode(int code) {
        return Arrays.stream(ChannelQueryFlowTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("排序枚举错误"));

    }
}
