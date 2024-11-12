package ag.orca.living.controller;

import ag.orca.living.api.market.LivingMarketService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.market.GiftSendRo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v3/market")
@Tag(name = "营销管理")
public class MarketController extends AbstractShareController {


    @DubboReference
    LivingMarketService livingMarketService;


    @Operation(summary = "送出礼物")
    @PostMapping("/gift/{roomId}/send")
    public OrcaResult<Void> sendGift(@PathVariable("roomId") Long roomId,
                                     @Validated @RequestBody GiftSendRo ro) {
        checkRoom(roomId);
        String uid = getUid();
        Long channel = getChannelId();
        livingMarketService.normalSendGift(ro, uid, channel);
        return OrcaResult.success();
    }


}
