package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryExemptWordRo", description = "免审词分页查询")
public class QueryExemptWordRo extends PageRo {

    @Schema(name = "name", description = "免审词组名称")
    private String name;


}
