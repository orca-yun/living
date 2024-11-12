package ag.orca.living.core.ro.query;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryRoomOverviewRo", description = "直播数据查询RO")
public class QueryRoomOverviewRo extends BaseBean {

    @Schema(name = "roomIds", description = "房间号列表")
    @NotEmpty(message = "房间号列表不能为空")
    private List<Long> roomIds;
}
