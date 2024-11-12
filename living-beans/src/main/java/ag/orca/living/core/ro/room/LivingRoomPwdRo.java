package ag.orca.living.core.ro.room;

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
@Schema(name = "LivingRoomPwdRo", description = "房间密码RO")
public class LivingRoomPwdRo extends BaseBean {

    @Schema(name = "roomId", description = "房间号码")
    @NotNull(message = "房间号码不能为空")
    private Long roomId;

    @Schema(name = "password", description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "role", description = "角色 0 主播密码 1 助理密码")
    @NotNull(message = "角色不能为空")
    private Integer role;
}
