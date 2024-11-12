package ag.orca.living.core.enums;

import ag.orca.living.errors.OrcaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    /**
     * 待支付
     */
    INIT(1, "待支付"),

    /**
     * 已支付
     */
    PAYED(2, "已支付"),

    /**
     * 已取消
     */
    CANCEL(3, "已取消"),

    /**
     * 超时未支付
     */
    TIMEOUT(4, "超时未支付"),

    /**
     * 退款
     */
    REFUND(5, "退款");

    private final int code;

    private final String describe;

    public static OrderStatusEnum ofCode(int code) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(s -> s.code == code)
                .findAny().orElseThrow(() -> OrcaException.error("订单状态无法映射"));

    }
}
