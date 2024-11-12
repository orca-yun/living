package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomPermission;
import ag.orca.living.core.enums.PermissionTypeEnum;
import ag.orca.living.core.ro.room.LivingRoomPermissionRo;
import ag.orca.living.core.vo.room.LivingRoomPermissionVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;

import java.util.Objects;

public class LivingRoomPermissionConvert {

    public static LivingRoomPermissionVo entityToVo(LivingRoomPermission entity) {
        return Objects.isNull(entity) ? null : LivingRoomPermissionVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .permissionJson(entity.getPermissionJson())
                .permissionType(entity.getPermissionType())
                .build();
    }

    public static LivingRoomPermission roToEntity(LivingRoomPermissionRo ro) {
        return Objects.isNull(ro) ? null : LivingRoomPermission.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .roomId(ro.getRoomId())
                .permissionJson(ro.getPermissionJson())
                .permissionType(ro.getPermissionType())
                .build();
    }

    public static LivingRoomPermission buildFromLivingRoom(LivingRoom room) {
        return LivingRoomPermission.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .permissionType(PermissionTypeEnum.NO_PWD.getCode())
                .permissionJson("")
                .build();
    }

    public static LivingRoomShareVo.PermissionShareVo entityToShareVo(LivingRoomPermission permission) {
        return Objects.isNull(permission) ? null : LivingRoomShareVo.PermissionShareVo
                .builder()
                .permissionJson(permission.getPermissionJson())
                .permissionType(permission.getPermissionType())
                .build();
    }
}
