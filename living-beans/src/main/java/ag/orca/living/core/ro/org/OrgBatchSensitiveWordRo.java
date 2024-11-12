package ag.orca.living.core.ro.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
@Schema(name = "OrgBatchSensitiveWordRo", description = "敏感词批量RO")
public class OrgBatchSensitiveWordRo extends BaseBean {
    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(name = "words", description = "敏感词列表")
    @NotEmpty(message = "敏感词列表不能为空")
    private List<String> words;
}
