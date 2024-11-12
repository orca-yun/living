package ag.orca.living.core.ro.org;

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
@Schema(name = "OrgRobotRo", description = "机器人库RO")
public class OrgRobotRo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    /**
     * 机器人昵称
     */
    @Schema(name = "nickname", description = "机器人昵称")
    @NotBlank(message = "机器人昵称不能为空")
    private String nickname;

    /**
     * 机器人头像
     */
    @Schema(name = "headIco", description = "机器人头像")
    private String headIco;
}
