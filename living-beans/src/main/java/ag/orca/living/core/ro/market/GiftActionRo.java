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
@Schema(name = "GiftActionRo", description = "礼物动作RO")
public class GiftActionRo extends BaseBean {

    @Schema(name = "id", description = "礼物ID")
    private Long id;

    @Schema(name = "roomId", description = "房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    @Schema(name = "status", description = "礼物状态 1 上架 2 下架")
    @NotNull(message = "礼物状态不能为空")
    private Integer status;


}
