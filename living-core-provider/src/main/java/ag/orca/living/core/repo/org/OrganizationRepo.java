package ag.orca.living.core.repo.org;


import ag.orca.living.core.dao.org.OrgAdminMapper;
import ag.orca.living.core.dao.org.OrganizationMapper;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.entity.org.Organization;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Repository
public class OrganizationRepo {

    @Resource
    OrgAdminMapper adminMapper;

    @Resource
    OrganizationMapper organizationMapper;


    public Optional<Organization> findOrgByAccount(String account) {
        OrgAdmin orgAdmin = adminMapper.findOrgAdminByAccount(account);
        return Objects.isNull(orgAdmin)
                ? Optional.empty()
                : findOrgByOrgId(orgAdmin.getOrgId());
    }

    public Optional<Organization> findOrgByOrgId(Long orgId) {
        return Optional.ofNullable(organizationMapper.findOrgById(orgId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void editOrganization(Organization organization) {
        organizationMapper.update(organization);
    }
}
