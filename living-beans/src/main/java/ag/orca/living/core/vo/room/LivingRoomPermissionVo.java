package ag.orca.living.core.vo.room;

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
public class LivingRoomPermissionVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "直播房间ID")
    private Long roomId;

    /**
     * 观看权限
     */
    @Schema(name = "permissionType", description = "观看权限: 1 无密码 2 密码 3 付费")
    private Integer permissionType;

    /**
     * 权限JSON
     */
    @Schema(name = "permissionJson", description = "权限配置(字符串) 权限为密码(填入密码文本), 付费(填入金额 分)")
    private String permissionJson;
}

