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
@Schema(name = "GiftSendRo", description = "礼物发送RO")
public class GiftSendRo extends BaseBean {

    @Schema(name = "id", description = "房间礼物ID")
    @NotNull(message = "房间礼物ID不能为空")
    private Long id;

    @Schema(name = "roomId", description = "房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;


}
