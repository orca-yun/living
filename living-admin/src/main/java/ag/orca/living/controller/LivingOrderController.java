package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.api.order.OrderInfoService;
import ag.orca.living.api.room.LivingRoomMarketGoodsItemService;
import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.ro.query.QueryOrderRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.order.OrderInfoExportVo;
import ag.orca.living.core.vo.order.OrderInfoExtVo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.alibaba.excel.util.DateUtils.defaultLocalDateFormat;

@RestController
@RequestMapping("/v1/order")
@Tag(name = "订单接口")
public class LivingOrderController extends AbstractAdminController {

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
        List<OrderInfoExtVo> orderExtVos = voToExtVo(vos);
        return OrcaPageResult.success(pair.getRight(), orderExtVos);
    }


    @Operation(summary = "订单列表导出")
    @GetMapping("/export")
    public OrcaResult<Void> exportOrderList(QueryOrderRo ro, HttpServletResponse response) throws IOException {
        Long orgId = getOrgId();
        List<OrderInfoVo> vos = orderInfoService.findListNoPage(orgId, ro);
        List<OrderInfoExtVo> orderExtVos = voToExtVo(vos);
        List<OrderInfoExportVo> exportVos = CommonConvert.map(orderExtVos, OrderInfoConvert::extVoToExportVo);
        String fileName = URLEncoder.encode(DateUtils.format(new Date(), defaultLocalDateFormat), Charset.defaultCharset());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(Charset.defaultCharset().name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OrderInfoExportVo.class).sheet().doWrite(exportVos);
        return OrcaResult.success();
    }

    private List<OrderInfoExtVo> voToExtVo(List<OrderInfoVo> vos) {
        if (CollectionUtils.isNotEmpty(vos)) {
            List<Long> roomIds = vos.stream().map(OrderInfoVo::getRoomId).distinct().toList();
            List<LivingRoomVo> roomVos = roomService.findListByIdList(roomIds);
            List<Long> goodsIds = vos.stream().map(OrderInfoVo::getGoodsId).distinct().toList();
            List<LivingRoomMarketGoodsItemVo> goodsItemVos = roomMarketGoodsItemService.findListByIdList(goodsIds);
            List<Long> channelIds = vos.stream().map(OrderInfoVo::getChannelId).distinct().toList();
            List<ChannelInfoVo> channels = channelInfoService.findListByChannelIdList(channelIds);
            return vos.stream().map(s -> {
                Optional<LivingRoomVo> roomVoOptional =
                        CommonConvert.filter(roomVos, r -> s.getRoomId().equals(r.getId())).stream().findFirst();
                Optional<LivingRoomMarketGoodsItemVo> itemOptional =
                        CommonConvert.filter(goodsItemVos, r -> s.getGoodsId().equals(r.getId())).stream().findFirst();
                Optional<ChannelInfoVo> channelOptional =
                        CommonConvert.filter(channels, r -> s.getChannelId().equals(r.getChannelId())).stream().findFirst();
                return OrderInfoConvert.voToOrderExtVo(s, roomVoOptional, itemOptional, channelOptional);
            }).toList();
        }
        return new ArrayList<>();

    }


}
