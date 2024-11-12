package ag.orca.living.core.ro.room;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "LivingRoomPermissionRo", description = "房间权限配置RO")
public class LivingRoomPermissionRo extends BaseBean {

    /**
     * ID
     */
    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "直播房间ID")
    @NotNull(message = "直播房间ID不能为空")
    private Long roomId;

    /**
     * 观看权限
     */
    @Schema(name = "permissionType", description = "观看权限: 1 无密码 2 密码 3 付费")
    @NotNull(message = "观看权限类型不能为空")
    private Integer permissionType;

    /**
     * 权限JSON
     */
    @Schema(name = "permissionJson", description = "权限配置(字符串) 权限为密码(填入密码文本), 付费(填入金额 分)")
    private String permissionJson;
}

