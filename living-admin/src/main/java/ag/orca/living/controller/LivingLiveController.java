package ag.orca.living.controller;

import ag.orca.living.api.live.LivingLiveService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.vo.live.LivingTriggerOplogVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static ag.orca.living.core.enums.LiveSourceEnum.DASHBOARD;

@RestController
@RequestMapping("/v1/live")
@Tag(name = "直播管理")
public class LivingLiveController extends AbstractAdminController {

    @DubboReference
    LivingLiveService liveService;

    @Operation(summary = "房间直播状态")
    @GetMapping("/{roomId}")
    public OrcaResult<Integer> operateStatus(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        return OrcaResult.success(optional.map(LivingRoomVo::getStatus).orElse(LiveRecordStatusEnum.lived.getCode()));
    }

    private void checkRoomIsLived(Long roomId) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        optional.ifPresent(roomVo -> {
            LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(roomVo.getStatus());
            OrcaAssert.match(status == LiveRecordStatusEnum.lived, I18nUtil.getMessage("room.living.stopped"));
        });
    }

    private void checkRoomIsLiving(Long roomId) {
        Optional<LivingRoomVo> optional = checkAndGetRoom(roomId);
        optional.ifPresent(roomVo -> {
            LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(roomVo.getStatus());
            OrcaAssert.match(status == LiveRecordStatusEnum.living, I18nUtil.getMessage("room.living.started"));
        });
    }

    @Operation(summary = "关闭直播")
    @PostMapping("/stop/{roomId}")
    public OrcaResult<Void> manualStopLive(@PathVariable("roomId") Long roomId) {
        checkRoomIsLived(roomId);
        liveService.manualOperateLiveStop(roomId, DASHBOARD);
        return OrcaResult.success();
    }

    @Operation(summary = "开始直播")
    @PostMapping("/start/{roomId}")
    public OrcaResult<Void> manualStartLive(@PathVariable("roomId") Long roomId) {
        checkAccountBalance();
        checkRoomIsLiving(roomId);
        liveService.manualOperateLiveStart(roomId, BoolEnum.FALSE.getCode(), DASHBOARD);
        return OrcaResult.success();
    }

//    @Operation(summary = "视频直播开播")
//    @PostMapping("/{roomId}/video/start")
//    public OrcaResult<Void> videoStart(@PathVariable("roomId") Long roomId,
//                                       @RequestBody LiveVideoOperateRo ro) {
//        checkAccountBalance();
//        checkRoomIsLiving(roomId);
//        liveService.videoOperateLiveStart(roomId, ro, VIDEO_PUSH);
//        return OrcaResult.success();
//    }

//    @Operation(summary = "视频直播关播")
//    @PostMapping("/{roomId}/video/stop")
//    public OrcaResult<Void> videoStop(@PathVariable("roomId") Long roomId) {
//        checkRoom(roomId);
//        checkRoomIsLived(roomId);
//        liveService.videoOperateLiveStop(roomId, VIDEO_PUSH);
//        return OrcaResult.success();
//    }


    @Operation(summary = "视频直播推流任务记录日志")
    @PostMapping("/{roomId}/video/oplog")
    public OrcaResult<LivingTriggerOplogVo> videoOplog(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        Optional<LivingTriggerOplogVo> optional = liveService.latestVideoOplog(roomId);
        return OrcaResult.success(optional.orElse(null));
    }
}
