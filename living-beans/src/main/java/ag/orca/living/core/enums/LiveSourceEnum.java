package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LiveSourceEnum {

    /**
     * 1 dashboard 触发
     */
    DASHBOARD(1),

    /**
     * 2 视频|镜像直播触发
     */
    VIDEO_PUSH(2),

    /**
     * 3 主播端触发
     */
    ANCHOR(3);

    private final int code;

    public static LiveSourceEnum ofCode(int code) {
        return Arrays.stream(LiveSourceEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("直播来源类型无法找到"));

    }
}
