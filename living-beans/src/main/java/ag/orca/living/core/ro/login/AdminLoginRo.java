package ag.orca.living.core.ro.login;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "AdminLoginRo", description = "登陆请求RO")
public class AdminLoginRo extends BaseBean {

    @Schema(name = "password", description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(name = "account", description = "用户帐号")
    @NotBlank(message = "帐号不能为空")
    private String account;

}
