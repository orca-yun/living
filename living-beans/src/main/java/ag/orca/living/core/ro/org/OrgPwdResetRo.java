package ag.orca.living.core.ro.org;

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
@Schema(name = "OrgPwdResetRo", description = "机构密码重置RO")
public class OrgPwdResetRo extends BaseBean {


    @Schema(name = "oldPwd", description = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;


    @Schema(name = "newPwd", description = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
