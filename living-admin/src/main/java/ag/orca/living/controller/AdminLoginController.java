package ag.orca.living.controller;

import ag.orca.living.api.base.OrganizationService;
import ag.orca.living.biz.TokenService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.ro.login.AdminLoginRo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
@Tag(name = "管理员登录")
public class AdminLoginController extends AbstractAdminController {

    @DubboReference
    OrganizationService organizationService;

    @Resource
    TokenService tokenService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public OrcaResult<String> login(@Validated @RequestBody AdminLoginRo ro) {
        Optional<OrgAdmin> optional = organizationService.findOrgAdminByAccount(ro.getAccount());
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("login.error"));
        OrgAdmin orgAdmin = optional.get();
        OrcaAssert.match(orgAdmin.getStatus() == BoolEnum.FALSE.getCode(), I18nUtil.getMessage("login.locked"));
        String encrypt = EncryptUtil.sha256Encrypt(ro.getPassword());
        OrcaAssert.match(!StringUtils.equals(orgAdmin.getPassword(), encrypt), I18nUtil.getMessage("login.error"));
        return OrcaResult.success(tokenService.generateToken(orgAdmin));
    }

}


