package ag.orca.living.core.enums;


import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VideoTypeEnum {

    /**
     * 1 真人直播,
     */
    REAL(1),


    /**
     * 2 视频直播(伪直播)
     */
    VIDEO(2);

    private final int code;

    public static VideoTypeEnum ofCode(int code) {
        return Arrays.stream(VideoTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("直播方式无法映射"));

    }


}
