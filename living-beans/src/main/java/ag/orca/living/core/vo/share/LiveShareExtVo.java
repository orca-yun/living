package ag.orca.living.core.vo.share;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LiveShareExtVo extends LiveShareVo {

    @Schema(name = "livingStatus", description = "0 直播中, 1 已经结束 或者 未开始")
    private Integer livingStatus;

    @Schema(name = "totalOnlineUser", description = "在线观看人数")
    private Long totalOnlineUser;

    @Schema(name = "notice", description = "直播间公告")
    private String notice;

}
