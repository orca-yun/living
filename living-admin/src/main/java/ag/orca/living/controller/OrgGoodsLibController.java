package ag.orca.living.controller;

import ag.orca.living.api.base.OrgGoodsLibService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.enums.PayTypeEnum;
import ag.orca.living.core.ro.org.OrgGoodsLibRo;
import ag.orca.living.core.ro.query.QueryGoodsRo;
import ag.orca.living.core.vo.org.OrgGoodsLibVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/org/goods")
@Tag(name = "机构商品库")
public class OrgGoodsLibController extends AbstractAdminController {

    @DubboReference
    OrgGoodsLibService goodsLibService;

    @Operation(summary = "商品列表")
    @GetMapping
    public OrcaPageResult<OrgGoodsLibVo> goodsList(QueryGoodsRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrgGoodsLibVo>, Long> pair = goodsLibService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    private void checkPayType(OrgGoodsLibRo ro) {
        Integer payType = ro.getPayType();
        PayTypeEnum typeEnum = PayTypeEnum.ofCode(payType);
        if (typeEnum == PayTypeEnum.JUMP) {
            OrcaAssert.match(StringUtils.isBlank(ro.getJumpPage()), I18nUtil.getMessage("pay.jump.blank"));
        }
        if (typeEnum == PayTypeEnum.QRCODE) {
            OrcaAssert.match(StringUtils.isBlank(ro.getQrcode()), I18nUtil.getMessage("pay.qrcode.blank"));
        }
        if (typeEnum == PayTypeEnum.MINI) {
            OrcaAssert.match(StringUtils.isBlank(ro.getMiniPage()), I18nUtil.getMessage("pay.mini.blank"));
        }
    }

    @Operation(summary = "保存商品")
    @PostMapping
    public OrcaResult<Void> saveGoods(@Validated @RequestBody OrgGoodsLibRo ro) {
        checkPayType(ro);
        goodsLibService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "编辑商品")
    @PutMapping
    public OrcaResult<Void> editGoods(@Validated @RequestBody OrgGoodsLibRo ro) {
        checkPayType(ro);
        goodsLibService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeGoods(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        goodsLibService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }


}
