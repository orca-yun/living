package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 清晰度: 1 流畅270p 2 标清480p 3 准高清720p 4 高清1080p 5 超高清2K
 */
@AllArgsConstructor
@Getter
public enum VideQualityEnum {

    /**
     * 流畅270p
     */
    flow(1),

    /**
     * 标清480p
     */
    STANDARD(2),

    /**
     * 准高清720p
     */
    HD_READY(3),

    /**
     * 高清1080p
     */
    HD(4),

    /**
     * 超高清2K
     */
    SUPER_HD(5);


    private final int code;

    public static VideQualityEnum ofCode(int code) {
        return Arrays.stream(VideQualityEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("清晰度无法映射"));

    }
}
