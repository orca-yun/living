package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryShareUserRo", description = "用户列表查询")
public class QueryShareUserRo extends PageRo {

    /**
     * 房间ID
     */
    @Schema(name = "roomId", description = "房间ID")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    /**
     * 房间ID
     */
    @Schema(name = "channelIds", description = "渠道ID列表")
    private List<Long> channelIds;


}
