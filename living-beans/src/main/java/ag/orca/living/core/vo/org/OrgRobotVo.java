package ag.orca.living.core.vo.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrgRobotVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 机器人编号
     */
    @Schema(name = "code", description = "机器人编号")
    private String code;

    /**
     * 机器人昵称
     */
    @Schema(name = "nickname", description = "机器人昵称")
    private String nickname;

    /**
     * 机器人头像
     */
    @Schema(name = "headIco", description = "机器人头像")
    private String headIco;
}
