package ag.orca.living.controller;

import ag.orca.living.api.room.*;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.room.*;
import ag.orca.living.core.vo.room.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/room/extend")
@Tag(name = "房间扩展配置")
public class LivingRoomExtendController extends AbstractAdminController {

    @DubboReference
    LivingRoomInteractService interactService;
    @DubboReference
    LivingRoomMarketGoodsService marketGoodsService;
    @DubboReference
    LivingRoomPageService pageService;
    @DubboReference
    LivingRoomPermissionService permissionService;
    @DubboReference
    LivingRoomMarketGoodsItemService marketGoodsItemService;

    @DubboReference
    LivingRoomMarketGiftItemService marketGiftItemService;

    @Operation(summary = "房间扩展配置")
    @GetMapping("/{roomId}")
    public OrcaResult<LivingRoomExtendVo> roomExtend(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        Long orgId = getOrgId();
        Optional<LivingRoomInteractVo> interact = interactService.findByRoomId(roomId);
        Optional<LivingRoomPageVo> page = pageService.findByRoomId(roomId);
        Optional<LivingRoomPermissionVo> permission = permissionService.findByRoomId(roomId);
        Optional<LivingRoomMarketGoodsVo> goods = marketGoodsService.findByRoomId(roomId);
        List<LivingRoomMarketGoodsItemVo> goodsItems = marketGoodsItemService.findListByRoomId(roomId);
        List<LivingRoomMarketGiftItemVo> giftItems = marketGiftItemService.findListByRoomId(roomId);
        return OrcaResult.success(LivingRoomExtendVo.builder()
                .orgId(orgId)
                .livingRoomId(roomId)
                .interact(interact.orElse(null))
                .page(page.orElse(null))
                .permission(permission.orElse(null))
                .goods(goods.orElse(null))
                .goodsItems(CollectionUtils.isEmpty(goodsItems) ? new ArrayList<>() : goodsItems)
                .giftItems(CollectionUtils.isEmpty(giftItems) ? new ArrayList<>() : giftItems)
                .build());
    }


    @Operation(summary = "保存互动设置")
    @PostMapping("{roomId}/interact")
    public OrcaResult<Void> saveInteract(@PathVariable("roomId") Long roomId,
                                         @Validated @RequestBody LivingRoomInteractRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        interactService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "保存页面配置")
    @PostMapping("{roomId}/page")
    public OrcaResult<Void> savePage(@PathVariable("roomId") Long roomId,
                                     @Validated @RequestBody LivingRoomPageRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        pageService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "保存权限配置")
    @PostMapping("{roomId}/permission")
    public OrcaResult<Void> savePermission(@PathVariable("roomId") Long roomId,
                                           @Validated @RequestBody LivingRoomPermissionRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        permissionService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "保存商品配置")
    @PostMapping("{roomId}/goods")
    public OrcaResult<Void> saveGoods(@PathVariable("roomId") Long roomId,
                                      @Validated @RequestBody LivingRoomMarketGoodsRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        marketGoodsService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "保存房间商品绑定")
    @PostMapping("/{roomId}/goods/item")
    public OrcaResult<Void> saveGoodsItem(@PathVariable("roomId") Long roomId,
                                          @Validated @RequestBody LivingRoomMarketGoodsItemRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        Long orgId = getOrgId();
        marketGoodsItemService.batchSave(orgId, roomId, ro);
        return OrcaResult.success();
    }


    @Operation(summary = "保存房间礼品绑定")
    @PostMapping("/{roomId}/gift/item")
    public OrcaResult<Void> saveGiftItem(@PathVariable("roomId") Long roomId,
                                         @Validated @RequestBody LivingRoomMarketGiftItemRo ro) {
        checkRoomId(roomId, ro.getRoomId());
        Long orgId = getOrgId();
        marketGiftItemService.batchSave(orgId, roomId, ro);
        return OrcaResult.success();
    }


}
