package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.VideQualityEnum;
import ag.orca.living.core.ro.room.LivingRoomRo;
import ag.orca.living.core.vo.media.LivingMediaLibVo;
import ag.orca.living.core.vo.room.*;

import java.util.Objects;
import java.util.Optional;

public class LivingRoomConvert {

    public static LivingRoomVo entityToVo(LivingRoom entity) {
        return Objects.isNull(entity) ? null
                : LivingRoomVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .name(entity.getName())
                .anchorPwd(entity.getAnchorPwd())
                .assistantPwd(entity.getAssistantPwd())
                .sharePwd(entity.getSharePwd())
                .livingTime(entity.getLivingTime())
                .scheduleTime(entity.getScheduleTime())
                .cover(entity.getCover())
                .logo(entity.getLogo())
                .remark(entity.getRemark())
                .livingType(entity.getLivingType())
                .videoType(entity.getVideoType())
                .videoQuality(entity.getVideoQuality())
                .mediaBizId(entity.getMediaBizId())
                .cycleTimes(entity.getCycleTimes())
                .status(entity.getStatus())
                .build();
    }

    public static LivingRoom roToEntity(LivingRoomRo ro) {
        return Objects.isNull(ro) ? null
                : LivingRoom.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .name(ro.getName())
//                .anchorPwd(ro.getAnchorPwd())
//                .assistantPwd(ro.getAssistantPwd())
//                .sharePwd(ro.getSharePwd())
                .livingTime(ro.getLivingTime())
                .scheduleTime(ro.getLivingTime().toLocalTime())
                .cover(ro.getCover())
                .logo(ro.getLogo())
                .remark(ro.getRemark())
                .livingType(ro.getLivingType())
                .videoType(ro.getVideoType())
                .videoQuality(VideQualityEnum.HD_READY.getCode())
                .mediaBizId(ro.getMediaBizId())
                .cycleTimes(Objects.isNull(ro.getCycleTimes()) ? 1 : ro.getCycleTimes())
                .build();
    }


    public static LivingRoomShareVo entityToShareVo(LivingRoom entity) {
        return Objects.isNull(entity) ? null :
                LivingRoomShareVo.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .livingTime(entity.getLivingTime())
                        .scheduleTime(entity.getScheduleTime())
                        .cover(entity.getCover())
                        .logo(entity.getLogo())
                        .remark(entity.getRemark())
                        .livingType(entity.getLivingType())
                        .videoType(entity.getVideoType())
                        .videoQuality(entity.getVideoQuality())
                        .status(entity.getStatus())
                        .build();
    }

    public static LivingRoomExtVo voToLivingRoomExtVo(LivingRoomVo roomVo,
                                                      Long totalUser) {
        return LivingRoomConvert.voToLivingRoomExtVo(roomVo, totalUser,
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
    }

    public static LivingRoomExtVo voToLivingRoomExtVo(LivingRoomVo roomVo,
                                                      Long totalUser,
                                                      Optional<LivingMediaLibVo> mediaLibOptional,
                                                      Optional<LivingRoomPageVo> pageOptional,
                                                      Optional<LivingRoomPermissionVo> permissionOptional) {
        return Objects.isNull(roomVo) ? null : LivingRoomExtVo.builder()
                .livingStatus(roomVo.getStatus())
                .id(roomVo.getId())
                .orgId(roomVo.getOrgId())
                .name(roomVo.getName())
                .anchorPwd(roomVo.getAnchorPwd())
                .assistantPwd(roomVo.getAssistantPwd())
                .sharePwd(roomVo.getSharePwd())
                .livingTime(roomVo.getLivingTime())
                .cover(roomVo.getCover())
                .logo(roomVo.getLogo())
                .remark(roomVo.getRemark())
                .livingType(roomVo.getLivingType())
                .videoType(roomVo.getVideoType())
                .videoQuality(roomVo.getVideoQuality())
                .mediaBizId(roomVo.getMediaBizId())
                .cycleTimes(roomVo.getCycleTimes())
                .status(roomVo.getStatus())
                .totalOnlineUser(totalUser)
                .mediaInfo(mediaLibOptional.orElse(null))
                .mobileLayout(pageOptional.map(LivingRoomPageVo::getMobileLayout).orElse(""))
                .permissionJson(permissionOptional.map(LivingRoomPermissionVo::getPermissionJson).orElse(""))
                .permissionType(permissionOptional.map(LivingRoomPermissionVo::getPermissionType).orElse(null))
                .build();

    }

    public static LivingRoomExtPermissionVo voToLivingRoomPermissionVo(LivingRoomVo roomVo,
                                                                       Optional<LivingRoomPermissionVo> optional) {
        return Objects.isNull(roomVo) ? null : LivingRoomExtPermissionVo.builder()
                .id(roomVo.getId())
                .orgId(roomVo.getOrgId())
                .name(roomVo.getName())
                .anchorPwd(roomVo.getAnchorPwd())
                .assistantPwd(roomVo.getAssistantPwd())
                .sharePwd(roomVo.getSharePwd())
                .livingTime(roomVo.getLivingTime())
                .cover(roomVo.getCover())
                .logo(roomVo.getLogo())
                .remark(roomVo.getRemark())
                .livingType(roomVo.getLivingType())
                .videoType(roomVo.getVideoType())
                .videoQuality(roomVo.getVideoQuality())
                .mediaBizId(roomVo.getMediaBizId())
                .cycleTimes(roomVo.getCycleTimes())
                .status(roomVo.getStatus())
                .permissionJson(optional.map(LivingRoomPermissionVo::getPermissionJson).orElse(""))
                .permissionType(optional.map(LivingRoomPermissionVo::getPermissionType).orElse(null))
                .build();

    }

}
