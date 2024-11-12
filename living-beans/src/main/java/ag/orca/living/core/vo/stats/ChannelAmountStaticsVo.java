package ag.orca.living.core.vo.stats;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ChannelAmountStaticsVo extends ChannelStaticsVo {

    /**
     * 真实成交金额
     */
    private Long realAmt;

    /**
     * 分成金额
     */
    private Long subCommission;

}
