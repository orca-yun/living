package ag.orca.living.controller;

import ag.orca.living.api.interact.InteractShareOnlineService;
import ag.orca.living.api.room.LivingRoomInteractService;
import ag.orca.living.api.room.LivingRoomMarketGoodsService;
import ag.orca.living.api.room.LivingRoomPageService;
import ag.orca.living.api.room.LivingRoomPermissionService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.convert.LivingRoomConvert;
import ag.orca.living.core.vo.room.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/v2/room")
@Tag(name = "房间管理")
public class LivingRoomController extends AbstractConsoleController {

    @DubboReference
    LivingRoomInteractService interactService;
    @DubboReference
    LivingRoomMarketGoodsService marketGoodsService;
    @DubboReference
    LivingRoomPageService pageService;
    @DubboReference
    LivingRoomPermissionService permissionService;
    @DubboReference
    InteractShareOnlineService shareOnlineService;


    @Operation(summary = "房间信息")
    @GetMapping("/meta")
    public OrcaResult<LivingRoomExtVo> roomMeta() {
        Long roomId = getRoomId();
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        Long totalUser = shareOnlineService.totalShareOnline(roomId);
        LivingRoomExtVo v = optional.map(s -> LivingRoomConvert.voToLivingRoomExtVo(s, totalUser)).orElse(null);
        return OrcaResult.success(v);
    }

    @Operation(summary = "房间扩展信息")
    @GetMapping("/extend/{roomId}")
    public OrcaResult<LivingRoomExtendVo> roomExtend(@PathVariable("roomId") Long roomId) {
        Long orgId = getOrgId();
        checkRoom(roomId);
        Optional<LivingRoomInteractVo> interact = interactService.findByRoomId(roomId);
        Optional<LivingRoomPageVo> page = pageService.findByRoomId(roomId);
        Optional<LivingRoomPermissionVo> permission = permissionService.findByRoomId(roomId);
        Optional<LivingRoomMarketGoodsVo> goods = marketGoodsService.findByRoomId(roomId);
        return OrcaResult.success(LivingRoomExtendVo.builder()
                .orgId(orgId)
                .livingRoomId(roomId)
                .interact(interact.orElse(null))
                .page(page.orElse(null))
                .permission(permission.orElse(null))
                .goods(goods.orElse(null))
                .goodsItems(new ArrayList<>())
                .giftItems(new ArrayList<>())
                .build());
    }


}
