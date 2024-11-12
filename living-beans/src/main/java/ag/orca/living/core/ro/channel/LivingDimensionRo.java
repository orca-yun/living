package ag.orca.living.core.ro.channel;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "LivingDimensionRo", description = "直播维度查询vo")
public class LivingDimensionRo extends BaseBean {

    /**
     * 直播间id
     */

    private Long roomId;

    /**
     * 引流人数排序字段 0正序 1倒叙
     */
    @Schema(name = "flow", description = "引流人数排序字段 0正序 1倒叙")
    private Integer flow;

    /**
     * 成交金额 0正序 1倒叙
     */
    @Schema(name = "amount", description = "成交金额 0正序 1倒叙")
    private Integer amount;


}
