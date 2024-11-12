package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.api.order.OrderInfoService;
import ag.orca.living.api.room.LivingRoomMarketGoodsItemService;
import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.ro.query.QueryOrderRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.order.OrderInfoExtVo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
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
@RequestMapping("/v2/order")
@Tag(name = "订单接口")
public class LivingOrderController extends AbstractConsoleController {

    @DubboReference
    OrderInfoService orderInfoService;

    @DubboReference
    LivingRoomService roomService;

    @DubboReference
    LivingRoomMarketGoodsItemService roomMarketGoodsItemService;

    @DubboReference
    ChannelInfoService channelInfoService;


    @Operation(summary = "订单列表")
    @GetMapping
    public OrcaPageResult<OrderInfoExtVo> orderList(QueryOrderRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrderInfoVo>, Long> pair = orderInfoService.findPageList(orgId, ro);
        List<OrderInfoVo> vos = pair.getLeft();
        List<Long> roomIds = vos.stream().map(OrderInfoVo::getRoomId).distinct().toList();
        List<LivingRoomVo> roomVos = roomService.findListByIdList(roomIds);
        List<Long> goodsIds = vos.stream().map(OrderInfoVo::getGoodsId).distinct().toList();
        List<LivingRoomMarketGoodsItemVo> goodsItemVos = roomMarketGoodsItemService.findListByIdList(goodsIds);
        List<Long> channelIds = vos.stream().map(OrderInfoVo::getChannelId).distinct().toList();
        List<ChannelInfoVo> channels = channelInfoService.findListByChannelIdList(channelIds);

        List<OrderInfoExtVo> orderExtVos = pair.getLeft().stream().map(s -> {
            Optional<LivingRoomVo> roomVoOptional =
                    CommonConvert.filter(roomVos, r -> s.getRoomId().equals(r.getId())).stream().findFirst();
            Optional<LivingRoomMarketGoodsItemVo> itemOptional =
                    CommonConvert.filter(goodsItemVos, r -> s.getGoodsId().equals(r.getId())).stream().findFirst();
            Optional<ChannelInfoVo> channelOptional =
                    CommonConvert.filter(channels, r -> s.getChannelId().equals(r.getChannelId())).stream().findFirst();
            return OrderInfoConvert.voToOrderExtVo(s, roomVoOptional, itemOptional, channelOptional);
        }).toList();
        return OrcaPageResult.success(pair.getRight(), orderExtVos);
    }
}
