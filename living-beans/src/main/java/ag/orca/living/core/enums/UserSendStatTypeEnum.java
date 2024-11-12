package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum UserSendStatTypeEnum {
    /**
     * 1 消息
     */
    msg(1),


    /**
     * 2 礼物
     */
    gift(2);

    private final int code;


    public static UserSendStatTypeEnum ofCode(int code) {
        return Arrays.stream(UserSendStatTypeEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("统计事件类型无法映射"));
    }
}
