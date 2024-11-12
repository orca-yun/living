package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VideoConvertStatusEnum {

    /**
     * 转码等待中
     */
    WAITING(0),

    /**
     * 转码成功
     */
    SUCCESS(1),

    /**
     * 转码失败
     */
    FAILED(2);

    private final int code;


    public static VideoConvertStatusEnum ofCode(int code) {
        return Arrays.stream(VideoConvertStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("转码状态无法映射"));
    }
}
