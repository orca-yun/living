package ag.orca.living.core.dao.control;

import ag.orca.living.core.entity.control.ControlAtmosphereTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ControlAtmosphereTemplateMapper {

    int logicDelById(@Param("id") Long id, @Param("dateTime") LocalDateTime dateTime);

    List<ControlAtmosphereTemplate> findListByRoomId(@Param("roomId") Long roomId);

    int updateByPrimaryKeySelective(ControlAtmosphereTemplate template);

    int insertSelective(ControlAtmosphereTemplate template);
}
