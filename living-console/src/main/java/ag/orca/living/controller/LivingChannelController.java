package ag.orca.living.controller;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/channel")
@Tag(name = "渠道管理")
public class LivingChannelController extends AbstractConsoleController {

    @DubboReference
    ChannelInfoService channelInfoService;

    @Operation(summary = "所有渠道列表")
    @GetMapping("/{roomId}")
    public OrcaResult<List<ChannelInfoVo>> channels(@PathVariable("roomId") Long roomId) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        List<ChannelInfoVo> infoVos = optional.map(s -> channelInfoService.findAllChannelByOrgId(s.getOrgId())).orElse(new ArrayList<>());
        return OrcaResult.success(infoVos);
    }
}
