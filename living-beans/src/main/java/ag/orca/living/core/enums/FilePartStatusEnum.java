package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FilePartStatusEnum {
    /**
     * 初始化
     */
    INIT(0),

    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 终断
     */
    ABORT(2);


    private final int code;


    public static FilePartStatusEnum ofCode(int code) {
        return Arrays.stream(FilePartStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("文件状态无法映射"));
    }
}
