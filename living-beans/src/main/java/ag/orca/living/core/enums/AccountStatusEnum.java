package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AccountStatusEnum {
    /**
     * 上架
     */
    NORMAL(1),

    /**
     * 冻结 锁定
     */
    LOCKED(2);


    private final int code;


    public static AccountStatusEnum ofCode(int code) {
        return Arrays.stream(AccountStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("账户状态无法映射"));
    }
}
