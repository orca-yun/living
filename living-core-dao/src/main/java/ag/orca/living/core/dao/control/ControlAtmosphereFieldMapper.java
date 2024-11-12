package ag.orca.living.core.dao.control;

import ag.orca.living.core.entity.control.ControlAtmosphereField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @description 针对表【t_atmosphere_field_control(氛围场控)】的数据库操作Mapper
 * @createDate 2024-04-13 23:48:06
 * @Entity ag.orca.living.core.entity.control.AtmosphereFieldControl
 */
@Mapper
public interface ControlAtmosphereFieldMapper {

    int insertSelective(ControlAtmosphereField record);

    ControlAtmosphereField selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ControlAtmosphereField record);

    ControlAtmosphereField findLatestControlByRoomId(@Param("roomId") Long roomId);

    /**
     * 更新氛围场控执行状态为已停止
     *
     * @param id 氛围场控ID
     * @return 受影响的行数
     */
    int updateExecStatusStoppedById(@Param("id") Long id);

}
