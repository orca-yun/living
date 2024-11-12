package ag.orca.living.core.dao.sys;

import ag.orca.living.core.entity.sys.SysRobot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRobotMapper {

    List<SysRobot> findAll();
}
