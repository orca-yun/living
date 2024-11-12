package ag.orca.living.core.ro.market;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "GoodsActionRo", description = "商品动作RO")
public class GoodsActionRo extends BaseBean {

    @Schema(name = "roomId", description = "房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    @Schema(name = "sellStatus", description = "商品状态 1 上架 2 下架 3 售罄")
    @NotNull(message = "商品状态不能为空")
    private Integer sellStatus;

    @Schema(name = "id", description = "商品ID")
    @NotNull(message = "商品ID不能为空")
    private Long id;
}
