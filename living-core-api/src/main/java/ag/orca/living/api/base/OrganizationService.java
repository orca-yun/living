package ag.orca.living.api.base;

import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.ro.org.OrganizationEditRo;
import ag.orca.living.core.vo.org.OrganizationVo;

import java.util.Optional;

public interface OrganizationService {

    /**
     * 查询机构【根据机构ID】
     *
     * @param orgId
     * @return
     */
    Optional<OrganizationVo> findOrgByOrgId(Long orgId);

    /**
     * 查询机构【根据账号】
     *
     * @param account
     * @return
     */
    Optional<Organization> findOrgByAccount(String account);

    /**
     * 查询管理员【根据账号】
     *
     * @param account
     * @return
     */
    Optional<OrgAdmin> findOrgAdminByAccount(String account);

    /**
     * 重置密码【根据账号】
     *
     * @param account
     * @param password
     */
    void resetOrgAdminPwd(String account, String password);


    /**
     * 编辑机构信息
     *
     * @param ro
     */
    void editOrganization(OrganizationEditRo ro);
}
