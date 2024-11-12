package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PushTaskStatusEnum {


    /**
     * 0 等待启动 1 运行中
     */
    waiting(0),
    /**
     * 1 运行中
     */
    running(1),

    /**
     * 2 已停止,正常结束(自动｜主动)
     */
    stop(2),

    /**
     * 3 已停止且失败
     */
    failed(3);

    private final int code;

    public static PushTaskStatusEnum ofCode(int code) {
        return Arrays.stream(PushTaskStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("推流状态无法映射"));

    }

    public boolean isEnd() {
        return stop == this || failed == this;
    }
}
