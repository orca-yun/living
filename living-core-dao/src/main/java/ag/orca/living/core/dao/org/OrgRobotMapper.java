package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgRobot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrgRobotMapper {
    List<OrgRobot> findListByOrgId(@Param("orgId") Long orgId);

    int countByOrgId(@Param("orgId") Long orgId);

    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    int update(OrgRobot robot);

    OrgRobot findById(@Param("id") Long id);

    List<OrgRobot> findListByOrgIdAndNicknameLike(@Param("orgId") Long orgId, @Param("nickname") String nickname);

    int batchInsert(@Param("robots") List<OrgRobot> robots);

    List<OrgRobot> findByIds(@Param("ids") List<Long> ids);

    OrgRobot findSameNickNameRobot(@Param("orgId") Long orgId, @Param("nickname") String nickname);
}
