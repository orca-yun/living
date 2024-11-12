package ag.orca.living.provider.order;

import ag.orca.living.api.order.OrderInfoService;
import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.enums.SellStatusEnum;
import ag.orca.living.core.repo.channel.ChannelInfoRepo;
import ag.orca.living.core.repo.order.OrderInfoRepo;
import ag.orca.living.core.repo.room.LivingRoomMarketGoodsItemRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.tlpay.TongLianRepo;
import ag.orca.living.core.ro.order.OrderInfoRo;
import ag.orca.living.core.ro.order.ShareOrderRo;
import ag.orca.living.core.ro.query.QueryOrderRo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.share.LivingShareUserAddressVo;
import ag.orca.living.util.I18nUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.OrcaConst.SEQ_LEN_5;

@DubboService
public class OrderInfoProvider implements OrderInfoService {

    @Resource
    OrderInfoRepo orderInfoRepo;

    @Resource
    ChannelInfoRepo channelInfoRepo;

    @Resource
    LivingRoomRepo livingRoomRepo;

    @Resource
    TongLianRepo tongLianRepo;

    @Resource
    LivingRoomMarketGoodsItemRepo livingRoomMarketGoodsItemRepo;

    @DubboReference
    LivingSeqService seqService;


    /**
     * 统一查询接口
     *
     * @param orderId 参数
     * @return TlQueryResult
     */
    @Override
    public Boolean orderIsPayed(Long orderId) {
        OrderInfo orderInfo = orderInfoRepo.findOrderInfoById(orderId);
        return Objects.isNull(orderInfo)
                ? Boolean.FALSE
                : (orderInfo.getOrderStatus() == OrderStatusEnum.PAYED.getCode());
    }


    @Override
    public Pair<List<OrderInfoVo>, Long> findPageList(Long orgId, QueryOrderRo ro) {
        //判断直播过滤状态有没有
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrderInfo> orders = orderInfoRepo.findListByOrgIdAndCondition(orgId,
                ro.getRoomId(), ro.getChannelIds(), ro.getOrderStatus(), null, ro.getOrderDate());
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orders);
        List<OrderInfoVo> vos = CommonConvert.map(pageInfo.getList(), OrderInfoConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    public List<OrderInfoVo> findListNoPage(Long orgId, QueryOrderRo ro) {
        List<OrderInfo> orders = orderInfoRepo.findListByOrgIdAndCondition(orgId,
                ro.getRoomId(), ro.getChannelIds(), ro.getOrderStatus(), null, ro.getOrderDate());
        return CommonConvert.map(orders, OrderInfoConvert::entityToVo);
    }

    @Override
    public List<OrderInfoVo> findUserOrderListByRoomIdAndUid(Long orgId, ShareOrderRo orderRo) {
        List<Integer> orderStatus = new ArrayList<>();
        orderStatus.add(OrderStatusEnum.PAYED.getCode());
        orderStatus.add(OrderStatusEnum.INIT.getCode());
        List<OrderInfo> orders = orderInfoRepo.findUserOrderListByRoomIdAndUidAndOrderDate(orgId,
                orderRo.getRoomId(), orderRo.getUserId(), orderRo.getOrderDate(), orderStatus);
        return CommonConvert.map(orders, OrderInfoConvert::entityToVo);
    }

    @Override
    public void cancelUnPayedOrder(Long orderId) {
        orderInfoRepo.cancelOrder(orderId);
    }


    @Override
    public Pair<Long, Integer> createOrder(AuthInfo authInfo, Long userId, OrderInfoRo ro) {
        Long roomId = authInfo.getRoomId();
        Long channelId = authInfo.getChannelId();
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        Optional<LivingRoomMarketGoodsItem> goodsItemOptional = livingRoomMarketGoodsItemRepo.findById(ro.getGoodsId());
        OrcaAssert.match(goodsItemOptional.isEmpty(), I18nUtil.getMessage("goods.not.exists"));
        LivingRoomMarketGoodsItem goodsItem = goodsItemOptional.get();
        SellStatusEnum sellStatus = SellStatusEnum.ofCode(goodsItem.getSellStatus());
        OrcaAssert.match(sellStatus != SellStatusEnum.ON_SHELVES, I18nUtil.getMessage("goods.sell.out"));
        SysTlpay sysTlpay = tongLianRepo.randomTlConfig();
        OrcaAssert.match(Objects.isNull(sysTlpay), I18nUtil.getMessage("tl.pay.mch.not.exists"));
        ChannelInfo channelInfo = channelInfoRepo.findFirstByChannelId(channelId);
        Long orderId = seqService.nextId();
        String orderNo = seqService.nextIdContainsTimestamp(SEQ_LEN_5);
        OrderInfo orderInfo = OrderInfoConvert.roToEntity(ro, orderId, orderNo, userId, authInfo, channelInfo, goodsItem, sysTlpay);
        orderInfoRepo.createOrder(orderInfo, goodsItem, sysTlpay);
        return Pair.of(orderId, goodsItem.getGoodType());
    }


    @Override
    public LivingShareUserAddressVo queryUserAddress(Long userId) {
        OrderInfo orderInfo = orderInfoRepo.findLatestOrderByUserId(userId);
        return OrderInfoConvert.entityToShareAddressVo(orderInfo);
    }


}
