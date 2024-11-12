package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SellStatusEnum {

    /**
     * 上架
     */
    ON_SHELVES(1),

    /**
     * 下架
     */
    OFF_SHELVES(2),

    /**
     * 售罄
     */
    SELL_OUT(3);

    private final int code;


    public static SellStatusEnum ofCode(int code) {
        return Arrays.stream(SellStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("商品状态无法映射"));
    }
}
