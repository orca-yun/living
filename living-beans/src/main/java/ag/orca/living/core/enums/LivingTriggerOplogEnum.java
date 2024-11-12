package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LivingTriggerOplogEnum {
    /**
     * 1 媒体库
     */
    MEDIA(1),


    /**
     * 2 直播记录
     */
    RECORD(2);

    private final int code;

    public static LivingTriggerOplogEnum ofCode(int code) {
        return Arrays.stream(LivingTriggerOplogEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("直播业务类型无法映射"));

    }
}
