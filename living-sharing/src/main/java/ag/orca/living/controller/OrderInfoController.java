package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.api.order.OrderInfoService;
import ag.orca.living.api.room.LivingRoomMarketGoodsItemService;
import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.api.tlpay.TongLianService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.ro.order.OrderInfoRo;
import ag.orca.living.core.ro.order.ShareOrderRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.order.OrderInfoExtVo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.core.vo.share.LivingShareUserAddressVo;
import ag.orca.living.core.vo.tlpay.TlUnifiedPayInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ag.orca.living.common.OrcaConst._100;
import static ag.orca.living.core.convert.OrderInfoConvert.buildUnifiedPayInfo;

@RestController
@RequestMapping("/v3/order")
@Tag(name = "订单")
public class OrderInfoController extends AbstractShareController {

    @DubboReference
    OrderInfoService orderInfoService;

    @DubboReference
    LivingRoomService roomService;

    @DubboReference
    LivingRoomMarketGoodsItemService roomMarketGoodsItemService;

    @DubboReference
    ChannelInfoService channelInfoService;


    @DubboReference
    private TongLianService tongLianService;

    @Operation(summary = "创建订单")
    @PostMapping
    public OrcaResult<Long> createOrder(@Validated @RequestBody OrderInfoRo ro) {
        AuthInfo authInfo = getAuthInfo();
        Long userId = getUserId();
        Pair<Long, Integer> pair = orderInfoService.createOrder(authInfo, userId, ro);
        return OrcaResult.success(pair.getLeft());
    }

    @Operation(summary = "取消订单[未支付的取消]")
    @PostMapping("/{orderId}")
    public OrcaResult<Void> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderInfoService.cancelUnPayedOrder(orderId);
        return OrcaResult.success();
    }

    /**
     * 获取统一支付信息
     *
     * @param orderId 订单号
     * @param payType 支付方式 {#{@link PaySceneEnum}} 该类型会直接影响payInfo的返回格式
     * @return R<TlPayInfoVo>
     */
    @Operation(summary = "统一支付")
    @GetMapping("/pay-info")
    public OrcaResult<TlUnifiedPayInfoVo> unifiedPay(@RequestParam("orderId") Long orderId,
                                                     @RequestParam("payType") Integer payType,
                                                     @RequestParam("callBackType") Integer callBackType) {
        Pair<Long, String> pair = tongLianService.unifiedPay(orderId, payType, callBackType);
        //支付的金额
        BigDecimal payTotal = BigDecimal.valueOf(pair.getLeft()).divide(_100, 2, RoundingMode.HALF_UP);
        //支付的金额
        String payInfo = pair.getRight();
        //构建Vo
        return OrcaResult.success(buildUnifiedPayInfo(orderId, payTotal, payType, payInfo));
    }


    /**
     * 交易查询
     *
     * @param orderId 订单号
     * @return R<TlQueryResult>
     */
    @Operation(summary = "交易查询")
    @GetMapping("/payed/{orderId}")
    public OrcaResult<Boolean> orderPayed(@PathVariable("orderId") Long orderId) {
        Boolean payed = orderInfoService.orderIsPayed(orderId);
        return OrcaResult.success(payed);
    }


    @Operation(summary = "查询用户上次实物地址")
    @GetMapping("/address")
    public OrcaResult<LivingShareUserAddressVo> queryUserAddress() {
        return OrcaResult.success(orderInfoService.queryUserAddress(getUserId()));
    }

    @Operation(summary = "查询人员今日直播间的订单列表")
    @GetMapping("/mine")
    public OrcaResult<List<OrderInfoExtVo>> mineOrderList() {
        Long userId = getUserId();
        Long orgId = getOrgId();
        Long roomId = getRoomId();
        ShareOrderRo ro = ShareOrderRo.builder().orderDate(LocalDate.now()).userId(userId).roomId(roomId).build();
        List<OrderInfoVo> vos = orderInfoService.findUserOrderListByRoomIdAndUid(orgId, ro);
        Optional<LivingRoomVo> roomVoOptional = roomService.findById(roomId);
        List<Long> goodsIds = vos.stream().map(OrderInfoVo::getGoodsId).distinct().toList();
        List<LivingRoomMarketGoodsItemVo> goodsItemVos = roomMarketGoodsItemService.findListByIdList(goodsIds);
        List<Long> channelIds = vos.stream().map(OrderInfoVo::getChannelId).distinct().toList();
        List<ChannelInfoVo> channels = channelInfoService.findListByChannelIdList(channelIds);
        List<OrderInfoExtVo> orderExtVos = vos.stream().map(s -> {
            Optional<LivingRoomMarketGoodsItemVo> itemOptional =
                    CommonConvert.filter(goodsItemVos, r -> s.getGoodsId().equals(r.getId())).stream().findFirst();
            Optional<ChannelInfoVo> channelOptional =
                    CommonConvert.filter(channels, r -> s.getChannelId().equals(r.getChannelId())).stream().findFirst();
            return OrderInfoConvert.voToOrderExtVo(s, roomVoOptional, itemOptional, channelOptional);
        }).toList();
        return OrcaResult.success(orderExtVos);
    }


}
