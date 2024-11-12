package ag.orca.living.core.ro.live;

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
@Schema(name = "LiveVideoOperateRo", description = "视频直播操作RO")
public class LiveVideoOperateRo extends BaseBean {

    @Schema(name = "mediaId", description = "媒体库ID")
    private Long mediaId;

    @Schema(name = "times", description = "执行次数")
    @Builder.Default
    private Integer times = 1;

    @Schema(name = "targetDuration", description = "目标长度")
    @Builder.Default
    private Long targetDuration = 0L;
}
