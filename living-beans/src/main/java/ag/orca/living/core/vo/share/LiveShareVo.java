package ag.orca.living.core.vo.share;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomShareVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LiveShareVo extends BaseBean {

    @Schema(name = "roomId", description = "房间ID")
    private Long roomId;

    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    @Schema(name = "orgName", description = "机构名称")
    private String orgName;

    @Schema(name = "orgLogo", description = "机构LOGO")
    private String orgLogo;

    @Schema(name = "channelId", description = "渠道端ID")
    private Long channelId;

    @Schema(name = "room", description = "房间信息")
    private LivingRoomShareVo room;

}
