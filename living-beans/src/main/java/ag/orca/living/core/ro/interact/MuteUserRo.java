package ag.orca.living.core.ro.interact;

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
@Schema(name = "MuteUserRo", description = "禁言用户RO")
public class MuteUserRo extends BaseBean {
    /**
     * 用户ID
     */
    @Schema(name = "uid", description = "用户ID")
    private String uid;

    /**
     * 昵称
     */
    @Schema(name = "nickname", description = "昵称")
    private String nickname;

    /**
     * 用户ID
     */
    @Schema(name = "roomId", description = "房间ID")
    private Long roomId;
}
