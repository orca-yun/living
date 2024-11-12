package ag.orca.living.core.dao.control;

import ag.orca.living.core.entity.control.ControlFieldMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @description 针对表【t_field_control_message(场控消息)】的数据库操作Mapper
 * @createDate 2024-04-13 23:48:06
 * @Entity ag.orca.living.core.entity.control.FieldControlMessage
 */
@Mapper
public interface ControlFieldMessageMapper {

    int insertSelective(ControlFieldMessage record);

    ControlFieldMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ControlFieldMessage record);


}
