package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AccountRecordTypeEnum {
    /**
     * 充值
     */
    CHARGE(1),

    /**
     * 消费
     */
    CONSUME(2);

    private final int code;

    public static AccountRecordTypeEnum ofCode(int code) {
        return Arrays.stream(AccountRecordTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("账户记录方式无法映射"));

    }

}
