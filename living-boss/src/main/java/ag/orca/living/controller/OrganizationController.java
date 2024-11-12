package ag.orca.living.controller;

import ag.orca.living.biz.OrganizationRepo;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.org.OrganizationRo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "保存机构信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v4/org")
public class OrganizationController extends AbstractBossController {


    @Resource
    OrganizationRepo organizationRepo;


    @Operation(summary = "保存机构信息")
    @PostMapping
    public OrcaResult<Void> saveOrgInfo(@Validated @RequestBody OrganizationRo ro) {
        organizationRepo.saveOrganization(ro);
        return OrcaResult.success();
    }

}
