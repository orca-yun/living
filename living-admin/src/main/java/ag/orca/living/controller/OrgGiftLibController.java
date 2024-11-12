package ag.orca.living.controller;

import ag.orca.living.api.base.OrgGiftLibService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.org.OrgGiftLibRo;
import ag.orca.living.core.ro.query.QueryGiftRo;
import ag.orca.living.core.vo.org.OrgGiftLibVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/org/gift")
@Tag(name = "机构礼品库")
public class OrgGiftLibController extends AbstractAdminController {

    @DubboReference
    OrgGiftLibService giftLibService;

    @Operation(summary = "礼品列表")
    @GetMapping
    public OrcaPageResult<OrgGiftLibVo> giftList(QueryGiftRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrgGiftLibVo>, Long> pair = giftLibService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "保存礼品")
    @PostMapping
    public OrcaResult<Void> saveGift(@Validated @RequestBody OrgGiftLibRo ro) {
        giftLibService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "编辑礼品")
    @PutMapping
    public OrcaResult<Void> editGift(@Validated @RequestBody OrgGiftLibRo ro) {
        giftLibService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除礼品")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeGift(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        giftLibService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }

}
