package ag.orca.living.core.ro.live;

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
@Schema(name = "LiveOperateRo", description = "直播操作RO")
public class LiveOperateRo extends BaseBean {

    @Schema(name = "needRecord", description = "是否需要录制 0 不需要 1 需要")
    private Integer needRecord;
}
