package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 消息类别, 1: 文本, 2: 礼物
 *
 * 
 * @date 2024-04-28
 */
@Getter
@AllArgsConstructor
public enum ControlMessageTypeEnum {

    /**
     * 文本
     */
    TEXT(1),

    /**
     * 礼物
     */
    GIFT(2);

    private final int code;

    public static ControlMessageTypeEnum ofCode(int code) {
        return Arrays.stream(values()).filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> OrcaException.error("剧本场控消息类型无法映射"));
    }
}
