package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface OrgAccountMapper {

    int insert(OrgAccount account);

    OrgAccount findFirstByOrgId(@Param("orgId") Long orgId);

    int debitBalance(@Param("orgId") Long orgId,
                     @Param("balance") Long balance,
                     @Param("dateTime") LocalDateTime dateTime);

    int updateStatus(@Param("orgId") Long orgId,
                     @Param("lockStatus") Integer lockStatus,
                     @Param("dateTime") LocalDateTime dateTime);

}
