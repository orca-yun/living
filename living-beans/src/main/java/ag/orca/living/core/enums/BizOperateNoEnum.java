package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum BizOperateNoEnum {

    CHARGE("CZ0001", "充值"),

    LIVING_CONSUME("XZB001", "直播消费"),

    VIDEO_CONSUME("XDB001", "点播消费"),

    STORAGE_CONSUME("XCC001", "直播消费");


    private final String no;

    private final String describe;

    public static BizOperateNoEnum ofCode(String no) {
        return Arrays.stream(BizOperateNoEnum.values())
                .filter(s -> Objects.equals(s.no, no))
                .findAny().orElseThrow(() -> OrcaException.error("业务代码无法映射"));

    }
}
