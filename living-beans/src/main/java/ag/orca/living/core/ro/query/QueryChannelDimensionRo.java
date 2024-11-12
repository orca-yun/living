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
@Schema(name = "QueryChannelDimensionRo", description = "渠道维度查询RO")
public class QueryChannelDimensionRo extends PageRo {

    /**
     * 渠道id
     */
    @Schema(name = "channelId", description = "渠道id")
    private Long channelId;

    /**
     * 引流人数排序字段 0正序 1倒叙
     */
    @Schema(name = "flow", description = "引流人数排序字段 0正序 1倒叙")
    private Integer flow;

    /**
     * 渠道ID 目前是6位数字
     */
    @Schema(name = "amount", description = "成交金额 0正序 1倒叙")
    private Integer amount;


}
