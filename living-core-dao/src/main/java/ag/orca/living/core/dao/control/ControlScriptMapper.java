package ag.orca.living.core.dao.control;

import ag.orca.living.core.entity.control.ControlScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @description 针对表【t_script(剧本)】的数据库操作Mapper
 * @createDate 2024-04-13 23:48:06
 * @Entity ag.orca.living.core.entity.control.Script
 */
@Mapper
public interface ControlScriptMapper {


    int insertBatch(@Param("records") List<ControlScript> records);

    ControlScript selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ControlScript record);

    List<ControlScript> selectByRoomId(@Param("roomId") Long roomId);

    int logicDel(@Param("ids") List<Long> ids, @Param("dateTime") LocalDateTime dateTime);

    List<ControlScript> selectListByIds(@Param("ids") List<Long> ids);
}
