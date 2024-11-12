package ag.orca.living.core.ro.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 添加剧本入参
 *
 * 
 * @date 2024-04-14
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "添加剧本入参")
public class AddScriptRo extends BaseBean {

    /**
     * 房间ID
     */
    @Schema(title = "房间ID")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    /**
     * 机器人ID列表
     */
    @Schema(title = "机器人ID列表")
    @NotEmpty(message = "机器人ID列表不能为空")
    private List<Long> robotIds;

}
