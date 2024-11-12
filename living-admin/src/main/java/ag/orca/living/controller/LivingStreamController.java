package ag.orca.living.controller;


import ag.orca.living.api.stream.LivingStreamService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.vo.stream.LivingE2eAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPushAddressVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stream")
@Tag(name = "流地址管理")
public class LivingStreamController extends AbstractAdminController {


    @DubboReference
    LivingStreamService streamService;


    @Operation(summary = "推流地址")
    @GetMapping("/{roomId}")
    public OrcaResult<LivingStreamPushAddressVo> pushAddress(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        checkAccountBalance();
        return OrcaResult.success(streamService.buildPushAddress(roomId));
    }


    @Operation(summary = "端到端地址")
    @GetMapping("/e2e/{roomId}")
    public OrcaResult<LivingE2eAddressVo> e2eAddress(@PathVariable("roomId") Long roomId,
                                                     @RequestParam(value = "channelId", required = false) Long channelId) {
        checkRoom(roomId);
        checkAccountBalance();
        Long orgId = getOrgId();
        return OrcaResult.success(streamService.buildE2eAddress(orgId, roomId, channelId));
    }
}
