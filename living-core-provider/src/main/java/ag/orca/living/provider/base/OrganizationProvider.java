package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrganizationService;
import ag.orca.living.core.convert.OrganizationConvert;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.repo.org.OrgAdminRepo;
import ag.orca.living.core.repo.org.OrganizationRepo;
import ag.orca.living.core.ro.org.OrganizationEditRo;
import ag.orca.living.core.vo.org.OrganizationVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Optional;

@Slf4j
@DubboService
public class OrganizationProvider implements OrganizationService {


    @Resource
    OrgAdminRepo adminRepo;

    @Resource
    OrganizationRepo organizationRepo;


    @Override
    public Optional<OrganizationVo> findOrgByOrgId(Long orgId) {
        Optional<Organization> optional = organizationRepo.findOrgByOrgId(orgId);
        return optional.map(OrganizationConvert::entityToVo);
    }

    @Override
    public Optional<Organization> findOrgByAccount(String account) {
        return organizationRepo.findOrgByAccount(account);
    }

    @Override
    public Optional<OrgAdmin> findOrgAdminByAccount(String account) {
        return adminRepo.findOrgAdminByAccount(account);
    }

    @Override
    public void resetOrgAdminPwd(String account, String password) {
        adminRepo.resetOrgAdminPwd(account, password);
    }


    @Override
    public void editOrganization(OrganizationEditRo ro) {
        Organization organization = OrganizationConvert.editRoToEntity(ro);
        organizationRepo.editOrganization(organization);
    }
}
