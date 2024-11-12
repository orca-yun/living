package ag.orca.living.core.dao.stream;

import ag.orca.living.core.entity.stream.LivingCoursewareItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingCoursewareItemMapper {

    int insert(LivingCoursewareItem item);

    List<LivingCoursewareItem> findListByRoomId(@Param("roomId") Long roomId);

    int logicRemoveCoursewareItemByCoursewareId(@Param("coursewareId") Long coursewareId, @Param("dateTime") LocalDateTime dateTime);
}
