package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VideoImageTypeEnum {

    /**
     * png
     */
    png(1),
    /**
     * gif
     */
    gif(2);

    private final int code;

    public static VideoImageTypeEnum fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst().orElseThrow(() -> OrcaException.error("图片类型错误"));

    }
}
