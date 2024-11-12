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
@Schema(name = "QueryGoodsRo", description = "商品分页查询")
public class QueryGoodsRo extends PageRo {
    @Schema(name = "name", description = "商品名称")
    private String name;
}
