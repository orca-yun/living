package ag.orca.living.controller;

import ag.orca.living.api.base.OrgExemptWordService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.org.OrgExemptWordRo;
import ag.orca.living.core.ro.query.QueryExemptWordRo;
import ag.orca.living.core.vo.org.OrgExemptWordVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/org/word/exempt")
@Tag(name = "免审词组")
public class OrgExemptWordController extends AbstractAdminController {

    @DubboReference
    OrgExemptWordService exemptWordService;


    @Operation(summary = "免审词组列表")
    @GetMapping
    public OrcaPageResult<OrgExemptWordVo> wordsList(QueryExemptWordRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrgExemptWordVo>, Long> pair = exemptWordService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "保存免审词组")
    @PostMapping
    public OrcaResult<Void> saveWords(@Validated @RequestBody OrgExemptWordRo ro) {
        exemptWordService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "编辑免审词组")
    @PutMapping
    public OrcaResult<Void> editWords(@Validated @RequestBody OrgExemptWordRo ro) {
        exemptWordService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除免审词组")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeWords(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        exemptWordService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }

}
