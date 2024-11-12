package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.channel.ChannelInfoRo;
import ag.orca.living.core.ro.query.QueryChannelInfoRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.channel.ChannelNameAndRatioVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/channel")
@Tag(name = "渠道管理")
public class ChannelController extends AbstractAdminController {

    @DubboReference
    ChannelInfoService channelInfoService;

    @Operation(summary = "新增渠道")
    @PostMapping
    public OrcaResult<Void> saveChannel(@Validated @RequestBody ChannelInfoRo ro) {
        Long orderId = getOrgId();
        channelInfoService.save(orderId, ro);
        return OrcaResult.success();
    }


    @Operation(summary = "修改渠道")
    @PutMapping()
    public OrcaResult<Void> editChannel(@Validated @RequestBody ChannelInfoRo ro) {
        channelInfoService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除渠道")
    @DeleteMapping("/{channelId}")
    public OrcaResult<Void> removeChannel(@PathVariable("channelId") Long channelId) {
        channelInfoService.removeByChannelId(getOrgId(), channelId);
        return OrcaResult.success();
    }

    @Operation(summary = "渠道列表")
    @GetMapping
    public OrcaPageResult<ChannelInfoVo> channelList(QueryChannelInfoRo ro) {
        ro.setOrgId(getOrgId());
        Pair<List<ChannelInfoVo>, Long> pair = channelInfoService.findPageList(ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "渠道列表不分页")
    @GetMapping("/all")
    public OrcaResult<List<ChannelInfoVo>> orgAllChannel() {
        Long orgId = getOrgId();
        return OrcaResult.success(channelInfoService.findAllChannelByOrgId(orgId));
    }


    @Operation(summary = "查询渠道名称和分佣比例")
    @GetMapping("/init")
    public OrcaResult<ChannelNameAndRatioVo> initChannelSelector() {
        Long orgId = getOrgId();
        List<ChannelInfoVo> channelInfos = channelInfoService.findAllChannelByOrgId(orgId);
        List<String> names = CommonConvert.distinctMap(channelInfos, ChannelInfoVo::getChannelName);
        List<Integer> ratios = CommonConvert.distinctMap(channelInfos, ChannelInfoVo::getCommissionRatio);
        return OrcaResult.success(ChannelNameAndRatioVo.builder().channelNames(names).ratios(ratios).build());
    }
}
