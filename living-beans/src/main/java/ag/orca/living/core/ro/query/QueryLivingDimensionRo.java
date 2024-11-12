package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryLivingDimensionRo", description = "直播维度查询vo")
public class QueryLivingDimensionRo extends PageRo {

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