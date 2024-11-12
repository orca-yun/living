package ag.orca.living.core.ro.login;

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
@Schema(name = "CrossLoginRo", description = "交叉登陆请求RO")
public class CrossLoginRo extends BaseBean {

    @Schema(name = "password", description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "roomId", description = "房间号")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    @Schema(name = "nickname", description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

}
