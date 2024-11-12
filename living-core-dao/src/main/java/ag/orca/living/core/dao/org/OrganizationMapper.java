package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    Organization findOrgById(@Param("id") Long id);

    int insert(Organization organization);

    int update(Organization organization);

    List<Organization> findAll();
}
