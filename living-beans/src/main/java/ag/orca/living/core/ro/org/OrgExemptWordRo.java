package ag.orca.living.core.ro.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(name = "OrgExemptWordRo", description = "免审词RO")
public class OrgExemptWordRo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;


    @Schema(name = "name", description = "免审词组名称")
    @NotBlank(message = "免审词组名称不能为空")
    private String name;

    /**
     * 免审词列表
     */
    @Schema(name = "words", description = "免审词列表")
    @NotEmpty(message = "免审词列表不能为空")
    private List<String> words;
}
