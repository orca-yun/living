package ag.orca.living.core.vo.room;

import ag.orca.living.core.vo.media.LivingMediaLibVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomExtVo extends LivingRoomExtPermissionVo {

    @Schema(name = "livingStatus", description = "0 直播中, 1 未开始")
    private Integer livingStatus;

    @Schema(name = "totalOnlineUser", description = "在线观看人数")
    private Long totalOnlineUser;

    @Schema(name = "mobileLayout", description = "移动端模板 1:全屏 2:二分屏 3:三分屏")
    private String mobileLayout;

    @Schema(name = "mediaInfo", description = "伪直播媒体信息")
    private LivingMediaLibVo mediaInfo;

}
