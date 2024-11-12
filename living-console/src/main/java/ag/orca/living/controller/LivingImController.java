package ag.orca.living.controller;

import ag.orca.living.api.im.LivingImService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.im.RobotSendRo;
import ag.orca.living.core.ro.im.RollbackMsgRo;
import ag.orca.living.core.ro.im.SendReplyMsgRo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/im")
@Tag(name = "IM相关")
public class LivingImController extends AbstractConsoleController {

    @DubboReference
    LivingImService livingImService;

    @Operation(summary = "撤回消息")
    @PostMapping("/rollback/{roomId}")
    public OrcaResult<Void> rollbackMsg(@PathVariable("roomId") Long roomId,
                                        @Validated @RequestBody RollbackMsgRo ro) {
        checkRoom(roomId);
        livingImService.rollbackMsg(roomId, ro, getAuthInfo());
        return OrcaResult.success();
    }

    @Operation(summary = "发送消息")
    @PostMapping("/send/{roomId}")
    public OrcaResult<Void> sendMsg(@PathVariable("roomId") Long roomId,
                                    @Validated @RequestBody SendReplyMsgRo ro) {
        checkRoom(roomId);
        livingImService.sendMsg(roomId, ro, getAuthInfo());
        return OrcaResult.success();
    }

    @Operation(summary = "机器人消息")
    @PostMapping("/send/robot/{roomId}")
    public OrcaResult<Void> robotSendMsg(@PathVariable("roomId") Long roomId,
                                         @Validated @RequestBody RobotSendRo ro) {
        checkRoom(roomId);
        livingImService.robotSendMsg(roomId, ro);
        return OrcaResult.success();
    }
}
