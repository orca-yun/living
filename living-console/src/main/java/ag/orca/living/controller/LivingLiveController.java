package ag.orca.living.controller;

import ag.orca.living.api.live.LivingLiveService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.ro.live.LiveOperateRo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v2/live")
@Tag(name = "直播管理")
public class LivingLiveController extends AbstractConsoleController {

    @DubboReference
    LivingLiveService liveService;


    @Operation(summary = "房间直播状态")
    @GetMapping("/{roomId}")
    public OrcaResult<Integer> operateStatus(@PathVariable("roomId") Long roomId) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        return OrcaResult.success(optional.map(LivingRoomVo::getStatus).orElse(LiveRecordStatusEnum.lived.getCode()));
    }

    @Operation(summary = "开始直播")
    @PostMapping("start/live/{roomId}")
    public OrcaResult<Void> operateLiveStart(@PathVariable("roomId") Long roomId,
                                             @RequestBody LiveOperateRo ro) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        optional.ifPresent(roomVo -> {
            LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(roomVo.getStatus());
            OrcaAssert.match(status == LiveRecordStatusEnum.living, I18nUtil.getMessage("room.living.started"));
            liveService.manualOperateLiveStart(roomId, ro.getNeedRecord(), LiveSourceEnum.ANCHOR);
        });
        return OrcaResult.success();
    }

    @Operation(summary = "关闭直播")
    @PostMapping("/stop/live/{roomId}")
    public OrcaResult<Void> operateLiveStop(@PathVariable("roomId") Long roomId) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        optional.ifPresent(roomVo -> {
            LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(roomVo.getStatus());
            OrcaAssert.match(status == LiveRecordStatusEnum.lived, I18nUtil.getMessage("room.living.stopped"));
            liveService.manualOperateLiveStop(roomId, LiveSourceEnum.ANCHOR);
        });
        return OrcaResult.success();
    }
}
