package ag.orca.living.core.entity.room;


import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingRoomPermission extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 观看权限: 1 无密码 2 密码 3 付费
     */
    private Integer permissionType;

    /**
     * 权限JSON
     */
    private String permissionJson;

}
