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
@Schema(name = "QuerySensitiveWordRo", description = "敏感词分页查询")
public class QuerySensitiveWordRo extends PageRo {

    @Schema(name = "words", description = "敏感词内容")
    private String words;


}
