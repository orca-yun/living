package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelDataService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.query.QueryChannelDimensionRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionDetailRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionRo;
import ag.orca.living.core.ro.query.QueryLivingDimensionRo;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import ag.orca.living.core.vo.channel.ChannelRoomDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/channel/data")
@Tag(name = "渠道数据看板")
public class ChannelDataBoardController extends AbstractAdminController {

    @DubboReference
    ChannelDataService channelDataService;


    @Operation(summary = "直播间维度看板统计数据")
    @GetMapping("/living/dimension/board/{roomId}")
    public OrcaResult<ChannelDataBoardVo> livingDimensionBoard(@PathVariable("roomId") Long roomId) {
        return OrcaResult.success(channelDataService.livingDimensionBoard(getOrgId(), roomId));
    }

    @Operation(summary = "直播维度数据列表")
    @GetMapping("/living/dimension")
    public OrcaPageResult<ChannelDataDetailVo> livingDimension(QueryLivingDimensionRo ro) {
        Pair<List<ChannelDataDetailVo>, Long> pair = channelDataService.findPageByLivingDimension(ro, getOrgId());
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }


    @Operation(summary = "渠道维度看板统计数据")
    @GetMapping("/channel/dimension/board/{channelId}")
    public OrcaResult<ChannelDataBoardVo> channelDimensionBoard(@PathVariable("channelId") Long channelId) {
        return OrcaResult.success(channelDataService.channelDimensionBoard(getOrgId(), channelId));
    }


    @Operation(summary = "渠道维度数据列表")
    @GetMapping("/channel/dimension")
    public OrcaPageResult<ChannelDataDetailVo> channelDimension(QueryChannelDimensionRo ro) {
        Pair<List<ChannelDataDetailVo>, Long> pair = channelDataService.findPageByChannelDimension(ro, getOrgId());
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }


    @Operation(summary = "时间维度看板统计数据")
    @GetMapping("/time/dimension/board/{date}")
    public OrcaResult<ChannelDataBoardVo> timeDimensionBoard(@PathVariable("date") LocalDate date) {
        return OrcaResult.success(channelDataService.timeDimensionBoard(getOrgId(), date));
    }

    @Operation(summary = "时间维度数据列表")
    @GetMapping("/time/dimension")
    public OrcaPageResult<ChannelDataDetailVo> timeDimension(QueryChannelTimeDimensionRo ro) {
        Pair<List<ChannelDataDetailVo>, Long> pair = channelDataService.findPageByTimeDimension(ro, getOrgId());
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }


    @Operation(summary = "时间维度详情")
    @GetMapping("/time/dimension/detail")
    public OrcaPageResult<ChannelRoomDetailVo> timeDimensionRoomDetail(QueryChannelTimeDimensionDetailRo ro) {
        Pair<List<ChannelRoomDetailVo>, Long> pair = channelDataService.timeDimensionRoomDetail(ro, getOrgId());
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }


}
