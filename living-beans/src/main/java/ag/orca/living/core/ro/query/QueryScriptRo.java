package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 剧本分页查询入参
 *
 * 
 * @date 2024-04-14
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "剧本分页查询入参")
public class QueryScriptRo extends PageRo {

    /**
     * 房间ID
     */
    @Schema(name = "roomId", description = "房间ID")
    @NotNull(message = "房间ID不能为空")
    private Long roomId;

}
