package ag.orca.living.core.convert;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.stream.LivingCourseware;
import ag.orca.living.core.entity.stream.LivingCoursewareItem;
import ag.orca.living.core.enums.CoursewareConvertStatusEnum;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LivingCoursewareConvert {

    public static LivingCoursewareVo entityToVo(LivingCourseware entity) {
        return Objects.isNull(entity) ? null
                : LivingCoursewareVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .name(entity.getName())
                .status(entity.getStatus())
                .images(new ArrayList<>())
                .build();
    }

    public static LivingCoursewareVo entityToVo(LivingCourseware entity, List<LivingCoursewareItem> items) {
        return LivingCoursewareVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .roomId(entity.getRoomId())
                .name(entity.getName())
                .status(entity.getStatus())
                .images(items.stream().map(LivingCoursewareItem::getImage).toList())
                .build();
    }

    public static LivingCourseware buildEntityFromRoom(LivingRoom room, String fileName) {
        return LivingCourseware.builder()
                .orgId(room.getOrgId())
                .roomId(room.getId())
                .name(fileName)
                .status(CoursewareConvertStatusEnum.ING.getCode())
                .build();
    }

    public static LivingCoursewareItem buildItemEntity(LivingCourseware courseware, String path) {
        return LivingCoursewareItem.builder()
                .coursewareId(courseware.getId())
                .orgId(courseware.getOrgId())
                .roomId(courseware.getRoomId())
                .image(path)
                .build();
    }
}
