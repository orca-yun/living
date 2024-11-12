package ag.orca.living.controller;

import ag.orca.living.api.stream.LivingStreamService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.vo.stream.LivingStreamPullAddressVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3/stream")
@Tag(name = "流地址管理")
public class LivingStreamController extends AbstractShareController {

    @DubboReference
    LivingStreamService streamService;


    @Operation(summary = "拉流地址")
    @GetMapping("/pull/{roomId}")
    public OrcaResult<LivingStreamPullAddressVo> pullAddress(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        return OrcaResult.success(streamService.buildPullAddress(roomId));
    }


}
