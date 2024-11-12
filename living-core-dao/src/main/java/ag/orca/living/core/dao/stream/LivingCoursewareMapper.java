package ag.orca.living.core.dao.stream;

import ag.orca.living.core.entity.stream.LivingCourseware;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingCoursewareMapper {
    int insert(LivingCourseware courseware);

    List<LivingCourseware> findListByRoomId(@Param("roomId") Long roomId);

    LivingCourseware findById(@Param("id") Long id);

    int updateStatusById(@Param("status") Integer status, @Param("id") Long id);

    int logicRemoveCoursewareById(@Param("id") Long id, @Param("dateTime") LocalDateTime dateTime);
}
