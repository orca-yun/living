package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LiveRecordStatusEnum {

    /**
     * 直播中
     */
    living(0),
    /**
     * 未开播
     */
    lived(1);

    private final int code;

    public static LiveRecordStatusEnum ofCode(int code) {
        return Arrays.stream(LiveRecordStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("直播记录状态无法映射"));

    }
}
