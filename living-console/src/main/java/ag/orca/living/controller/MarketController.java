package ag.orca.living.controller;


import ag.orca.living.api.market.LivingMarketService;
import ag.orca.living.api.room.LivingRoomMarketGiftItemService;
import ag.orca.living.api.room.LivingRoomMarketGoodsItemService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.market.*;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/market")
@Tag(name = "营销管理")
public class MarketController extends AbstractConsoleController {

    @DubboReference
    LivingRoomMarketGoodsItemService marketGoodsItemService;

    @DubboReference
    LivingMarketService livingMarketService;


    @DubboReference
    LivingRoomMarketGiftItemService marketGiftItemService;

    @Operation(summary = "房间商品列表")
    @GetMapping("/goods/{roomId}")
    public OrcaResult<List<LivingRoomMarketGoodsItemVo>> goodsItem(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        List<LivingRoomMarketGoodsItemVo> vos = marketGoodsItemService.findListByRoomId(roomId);
        return OrcaResult.success(vos);
    }


    @Operation(summary = "房间商品重排序")
    @PostMapping("/goods/{roomId}/sort")
    public OrcaResult<Void> sortGoods(@PathVariable("roomId") Long roomId,
                                      @Validated @RequestBody GoodSortedRo ro) {
        checkRoom(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.sortedGoodsAction(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "房间礼物列表")
    @GetMapping("/gift/{roomId}")
    public OrcaResult<List<LivingRoomMarketGiftItemVo>> giftItem(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        List<LivingRoomMarketGiftItemVo> vos = marketGiftItemService.findListByRoomId(roomId);
        return OrcaResult.success(vos);
    }

    @Operation(summary = "房间礼物重排序")
    @PostMapping("/gift/{roomId}/sort")
    public OrcaResult<Void> sortGift(@PathVariable("roomId") Long roomId,
                                     @Validated @RequestBody GiftSortedRo ro) {
        checkRoom(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.sortedGiftAction(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "商品互动上下架售罄")
    @PostMapping("/goods/{roomId}/action")
    public OrcaResult<Void> goodsAction(@PathVariable("roomId") Long roomId,
                                        @Validated @RequestBody GoodsActionRo ro) {
        checkRoomId(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.goodsAction(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "商品互动上下架售罄")
    @PostMapping("/goods/{roomId}/action/batch")
    public OrcaResult<Void> goodsBatchAction(@PathVariable("roomId") Long roomId,
                                             @Validated @RequestBody GoodsBatchActionRo ro) {
        checkRoomId(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.goodsBatchAction(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "商品推荐")
    @PostMapping("/goods/{roomId}/recommend")
    public OrcaResult<Void> goodsRecommend(@PathVariable("roomId") Long roomId,
                                           @Validated @RequestBody GoodsRecommendRo ro) {
        checkRoomId(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.goodRecommend(ro);
        return OrcaResult.success();
    }


    @Operation(summary = "礼物互动上下架")
    @PostMapping("/gift/{roomId}/action")
    public OrcaResult<Void> gifAction(@PathVariable("roomId") Long roomId,
                                      @Validated @RequestBody GiftActionRo ro) {
        checkRoomId(roomId);
        checkRoom(ro.getRoomId());
        livingMarketService.giftAction(ro);
        return OrcaResult.success();
    }


    @Operation(summary = "机器人送出礼物")
    @PostMapping("/gift/{roomId}/robot/send")
    public OrcaResult<Void> robotSendGift(@PathVariable("roomId") Long roomId,
                                          @Validated @RequestBody RobotGiftSendRo ro) {
        livingMarketService.robotSendGift(ro);
        return OrcaResult.success();
    }

}
