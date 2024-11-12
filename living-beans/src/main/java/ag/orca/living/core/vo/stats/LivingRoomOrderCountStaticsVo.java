package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomOrderCountStaticsVo extends BaseBean {

    private Long orderNum;

    private Long payedOrderNum;

    private Long waitOrderNum;

    private Long cancelOrderNum;

    private Long timeoutOrderNum;
}
