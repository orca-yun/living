package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgShortDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgShortDomainMapper {

    List<OrgShortDomain> findAllDomainByOrgId(@Param("orgId") Long orgId);
}
