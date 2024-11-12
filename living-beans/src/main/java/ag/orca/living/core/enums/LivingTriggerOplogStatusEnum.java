package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LivingTriggerOplogStatusEnum {
    /**
     * 1 等待启动
     */
    INIT(0),


    /**
     * 2 运行中
     */
    RUNNING(1),


    /**
     * 2 已停止,正常结束(自动｜主动)
     */
    END(2),


    /**
     * 2 运行中
     */
    FAIL(3),

    ;

    private final int code;

    public static LivingTriggerOplogStatusEnum ofCode(int code) {
        return Arrays.stream(LivingTriggerOplogStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("推流任务状态映射错误"));

    }

    public static boolean isNotEnd(int code) {
        LivingTriggerOplogStatusEnum status = ofCode(code);
        return status == INIT || status == RUNNING;
    }
}
