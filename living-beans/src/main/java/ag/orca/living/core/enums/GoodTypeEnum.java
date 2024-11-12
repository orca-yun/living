package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品类型
 */
@Getter
@AllArgsConstructor
public enum GoodTypeEnum {

    /**
     * 虚拟
     */
    VIRTUAL(1, "虚拟商品"),

    /**
     * 真实
     */
    REALITY(2, "实物商品");


    private final int code;

    private final String describe;


    public static GoodTypeEnum ofCode(int code) {
        return Arrays.stream(GoodTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("商品类型无法映射"));
    }
}
