package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum GiftStatusEnum {

    /**
     * 上架
     */
    ON_SHELVES(1),

    /**
     * 下架
     */
    OFF_SHELVES(2);


    private final int code;


    public static GiftStatusEnum ofCode(int code) {
        return Arrays.stream(GiftStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("礼物状态无法映射"));
    }
}
