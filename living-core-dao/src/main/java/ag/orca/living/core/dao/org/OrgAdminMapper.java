package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface OrgAdminMapper {

    int resetOrgAdminPwdById(@Param("id") Long id,
                             @Param("password") String password,
                             @Param("dateTime") LocalDateTime dateTime);

    OrgAdmin findOrgAdminByAccount(@Param("account") String account);

    int insert(OrgAdmin admin);

}
