package ag.orca.living.core.ro.im;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "RobotSendRo", description = "机器人发送消息RO")
public class RobotSendRo extends BaseBean {

    @Schema(name = "robotId", description = "机器人ID")
    @NotNull(message = "机器人ID不能为空")
    private Long robotId;

    @Schema(name = "data", description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String data;

    @Schema(name = "replyData", description = "回复的内容")
    private String replyData;
}
