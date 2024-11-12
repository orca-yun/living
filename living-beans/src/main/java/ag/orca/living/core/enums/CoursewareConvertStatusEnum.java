package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CoursewareConvertStatusEnum {
    /**
     * 1 转换中
     */
    ING(1),


    /**
     * 2 转换成功
     */
    SUCCESS(2),

    /**
     * 3 转换失败
     */
    FAILED(3);


    private final int code;


    public static CoursewareConvertStatusEnum ofCode(int code) {
        return Arrays.stream(CoursewareConvertStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("课件转换状态无法映射"));
    }
}
