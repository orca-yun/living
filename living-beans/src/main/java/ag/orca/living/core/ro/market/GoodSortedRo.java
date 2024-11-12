package ag.orca.living.core.ro.market;

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
@Schema(name = "GoodSortedRo", description = "商品排序RO")
public class GoodSortedRo extends BaseBean {

    @Schema(name = "roomId", description = "房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    @Schema(name = "itemIds", description = "商品ID列表")
    @NotEmpty(message = "商品ID列表不能为空")
    private List<Long> itemIds;
}
