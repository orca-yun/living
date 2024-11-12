package ag.orca.living.controller;

import ag.orca.living.api.base.OrgAccountService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.query.QueryAccountRecordRo;
import ag.orca.living.core.vo.org.OrgAccountRecordVo;
import ag.orca.living.core.vo.org.OrgAccountVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/org/account")
@Tag(name = "账户信息")
public class OrgAccountController extends AbstractAdminController {

    @DubboReference
    OrgAccountService orgAccountService;

    @Operation(summary = "查询账户信息")
    @GetMapping
    public OrcaResult<OrgAccountVo> accountInfo() {
        Optional<OrgAccountVo> o = orgAccountService.findOrgAccountByOrgId(getOrgId());
        return OrcaResult.success(o.orElse(new OrgAccountVo()));
    }


    @Operation(summary = "查询账户流水记录")
    @GetMapping("/record")
    public OrcaPageResult<OrgAccountRecordVo> accountRecords(QueryAccountRecordRo ro) {
        Pair<List<OrgAccountRecordVo>, Long> pair = orgAccountService.findAccountRecordPageList(getOrgId(), ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }


}
