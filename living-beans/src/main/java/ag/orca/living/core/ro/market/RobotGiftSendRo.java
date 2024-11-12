package ag.orca.living.core.ro.market;

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
@Schema(name = "RobotGiftSendRo", description = "机器人礼物送出RO")
public class RobotGiftSendRo extends GiftSendRo {

    /**
     * 机器人ID
     */
    @Schema(name = "robotId", description = "机器人ID")
    @NotNull(message = "机器人ID不能为空")
    private Long robotId;
}
