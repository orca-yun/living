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
public class LivingRoomOverviewVo extends BaseBean {

    @Schema(name = "pv", description = "总观看人次(PV)")
    @Builder.Default
    private Long pv = 0L;

    @Schema(name = "uv", description = "总观看人次(UV)")
    @Builder.Default
    private Long uv = 0L;


    @Schema(name = "onlineNum", description = "在线人数峰值(人)")
    @Builder.Default
    private Long onlineNum = 0L;

    @Schema(name = "greater5Num", description = "停留超过5分钟人数(人)")
    @Builder.Default
    private Long greater5Num = 0L;


    @Schema(name = "totalViewDuration", description = "总观看时长(秒)")
    @Builder.Default
    private Long totalViewDuration = 0L;


    @Schema(name = "avgViewDuration", description = "平均观看时长(秒)")
    @Builder.Default
    private Long avgViewDuration = 0L;
}
