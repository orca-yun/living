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
@Schema(name = "QueryChannelInfoRo", description = "渠道信息分页查询")
public class QueryChannelInfoRo extends PageRo {

    @Schema(name = "channelName", description = "渠道名称")
    private String channelName;

    @Schema(name = "commissionRatio", description = "分佣比例 百分数1-100")
    private Integer commissionRatio;

    @Schema(name = "commissionMethod", description = "分佣方式 1 自动分佣 2 手动分佣")
    private Integer commissionMethod;

    @Schema(name = "commissionPeriod", description = "分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算")
    private Integer commissionPeriod;

    private Long orgId;

}
