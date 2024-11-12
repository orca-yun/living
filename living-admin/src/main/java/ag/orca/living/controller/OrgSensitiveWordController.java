package ag.orca.living.controller;

import ag.orca.living.api.base.OrgSensitiveWordService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.org.OrgBatchSensitiveWordRo;
import ag.orca.living.core.ro.org.OrgSensitiveWordRo;
import ag.orca.living.core.ro.query.QuerySensitiveWordRo;
import ag.orca.living.core.vo.org.OrgSensitiveWordVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/org/word/sensitive")
@Tag(name = "敏感词")
public class OrgSensitiveWordController extends AbstractAdminController {

    @DubboReference
    OrgSensitiveWordService sensitiveWordService;


    @Operation(summary = "敏感词列表")
    @GetMapping
    public OrcaPageResult<OrgSensitiveWordVo> wordsList(QuerySensitiveWordRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrgSensitiveWordVo>, Long> pair = sensitiveWordService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "保存敏感词")
    @PostMapping
    public OrcaResult<Void> saveWords(@Validated @RequestBody OrgBatchSensitiveWordRo ro) {
        sensitiveWordService.batchSave(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "编辑敏感词")
    @PutMapping
    public OrcaResult<Void> editWords(@Validated @RequestBody OrgSensitiveWordRo ro) {
        sensitiveWordService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除敏感词")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeWords(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        sensitiveWordService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }

}
