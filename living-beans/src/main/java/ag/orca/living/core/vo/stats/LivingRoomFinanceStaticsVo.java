package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomFinanceStaticsVo extends BaseBean {

    @Schema(name = "totalOrderAmount", description = "总共订单总金额")
    @Builder.Default
    private Long totalOrderAmount = 0L;

    @Schema(name = "payedOrderAmount", description = "已支付总金额")
    @Builder.Default
    private Long payedOrderAmount = 0L;

    @Schema(name = "unPayedOrderAmount", description = "未支付总金额")
    @Builder.Default
    private Long unPayedOrderAmount = 0L;

    @Schema(name = "waitOrderAmount", description = "等待支付总金额")
    @Builder.Default
    private Long waitOrderAmount = 0L;

}
