package ag.orca.living.core.ro;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "PageRo", description = "分页请求RO")
public class PageRo extends BaseBean {

    @Builder.Default
    @Schema(name = "page", description = "页码")
    private int page = 1;


    @Builder.Default
    @Schema(name = "pageSize", description = "页大小")
    private int pageSize = 20;

    @Builder.Default
    @Schema(name = "sort", description = "排序")
    private String sort = "create_time DESC";
}
