package ag.orca.living.core.vo.channel;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 渠道数据看板VO
 *
 * 
 * @date 2024-04-27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道数据看板VO")
public class ChannelDataBoardVo extends BaseBean {

    /**
     * 引入流人数
     */
    @Schema(title = "引入流人数")
    private Integer totalFlowPeople;

    /**
     * 成交金额(分)
     */
    @Schema(title = "成交金额(分)")
    private Long totalTransactionAmount;

    /**
     * 分佣金额(分)
     */
    @Schema(title = "分佣金额(分)")
    private Long totalSubCommission;


}
