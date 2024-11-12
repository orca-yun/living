package ag.orca.living.controller;

import ag.orca.living.api.base.OrganizationService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.ro.org.OrgPwdResetRo;
import ag.orca.living.core.ro.org.OrganizationEditRo;
import ag.orca.living.core.vo.org.OrganizationVo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/v1/org/info")
@Tag(name = "机构配置")
public class OrganizationController extends AbstractAdminController {

    @DubboReference
    OrganizationService organizationService;

    @Operation(summary = "获取机构信息")
    @GetMapping
    public OrcaResult<OrganizationVo> orgInfo() {
        Long orgId = getOrgId();
        Optional<OrganizationVo> optional = organizationService.findOrgByOrgId(orgId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("org.not.exists"));
        return OrcaResult.success(optional.get());
    }

    @Operation(summary = "更新机构信息")
    @PutMapping
    public OrcaResult<Void> editOrgInfo(@Validated @RequestBody OrganizationEditRo ro) {
        organizationService.editOrganization(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "重置管理员密码")
    @PostMapping("/pwd")
    public OrcaResult<Void> resetOrgPwd(@Validated @RequestBody OrgPwdResetRo ro) {
        String account = getAccount();
        Optional<OrgAdmin> optional = organizationService.findOrgAdminByAccount(account);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("org.admin.not.exists"));
        String encryptOldPwd = EncryptUtil.sha256Encrypt(ro.getOldPwd());
        OrcaAssert.match(!StringUtils.equals(encryptOldPwd, optional.get().getPassword()), I18nUtil.getMessage("org.admin.reset.pwd.error"));
        organizationService.resetOrgAdminPwd(account, ro.getNewPwd());
        return OrcaResult.success();
    }


}
