package ag.orca.living.core.ro.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "剧本入参")
public class BatchScriptRo extends BaseBean {

    @Schema(title = "剧本ID列表")
    @NotEmpty(message = "剧本ID列表不能为空")
    private List<Long> ids;
}
