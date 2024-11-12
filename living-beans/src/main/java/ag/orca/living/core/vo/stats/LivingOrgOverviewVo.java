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
public class LivingOrgOverviewVo extends BaseBean {

    @Schema(name = "liveNum", description = "开播次数")
    @Builder.Default
    private Long liveNum = 0L;

    @Schema(name = "liveUv", description = "观看直播人数")
    @Builder.Default
    private Long liveUv = 0L;

    @Schema(name = "liveUv", description = "观看点播人数")
    @Builder.Default
    private Long playUv = 0L;

    @Schema(name = "storage", description = "云存储(字节)")
    @Builder.Default
    private Long storage = 0L;
}
