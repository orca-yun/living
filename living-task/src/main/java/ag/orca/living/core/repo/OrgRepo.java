package ag.orca.living.core.repo;

import ag.orca.living.core.dao.org.OrganizationMapper;
import ag.orca.living.core.entity.org.Organization;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgRepo {

    @Resource
    OrganizationMapper organizationMapper;

    public List<Organization> findAllOrg() {
        return organizationMapper.findAll();
    }
}
