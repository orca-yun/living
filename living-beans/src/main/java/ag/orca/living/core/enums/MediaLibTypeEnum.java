package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MediaLibTypeEnum {

    /**
     * 1 手动上传
     */
    MANUAL(1),

    /**
     * 2 从录制而来
     */
    RECORD(2);

    private final int code;

    public static MediaLibTypeEnum ofCode(int code) {
        return Arrays.stream(MediaLibTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("媒体库类型无法映射"));

    }
}
