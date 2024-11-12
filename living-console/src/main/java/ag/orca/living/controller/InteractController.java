package ag.orca.living.controller;

import ag.orca.living.api.base.OrgRobotService;
import ag.orca.living.api.interact.InteractAssiOnlineService;
import ag.orca.living.api.interact.InteractBlacklistService;
import ag.orca.living.api.interact.InteractMuteService;
import ag.orca.living.api.interact.InteractShareOnlineService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.enums.OperateAllMuteEnum;
import ag.orca.living.core.ro.PageRo;
import ag.orca.living.core.ro.interact.BlacklistUserRo;
import ag.orca.living.core.ro.interact.MuteUserRo;
import ag.orca.living.core.vo.interact.AssiOnlineVo;
import ag.orca.living.core.vo.interact.BlacklistUserVo;
import ag.orca.living.core.vo.interact.MuteUserVo;
import ag.orca.living.core.vo.interact.ShareOnlineVo;
import ag.orca.living.core.vo.org.OrgRobotVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 互动操作
 * - 拉黑
 * - 禁言
 * -
 */
@RestController
@RequestMapping("/v2/interact")
@Tag(name = "互动操作管理")
public class InteractController extends AbstractConsoleController {

    @DubboReference
    OrgRobotService robotService;

    @DubboReference
    InteractBlacklistService blacklistService;
    @DubboReference
    InteractMuteService muteService;
    @DubboReference
    InteractAssiOnlineService assiOnlineService;
    @DubboReference
    InteractShareOnlineService shareOnlineService;


    @Operation(summary = "机器人列表")
    @GetMapping("/robot")
    public OrcaResult<List<OrgRobotVo>> robotList() {
        Long orgId = getOrgId();
        List<OrgRobotVo> robots = robotService.findList(orgId);
        return OrcaResult.success(robots);
    }


    @Operation(summary = "助理在线列表")
    @GetMapping("/online/assi/{roomId}")
    public OrcaResult<List<AssiOnlineVo>> assiOnline(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        return OrcaResult.success(assiOnlineService.findAssiOnlineList(roomId));
    }

    @Operation(summary = "学员在线列表")
    @GetMapping("/online/share/{roomId}")
    public OrcaPageResult<ShareOnlineVo> shareOnline(@PathVariable("roomId") Long roomId,
                                                     PageRo ro) {
        checkRoom(roomId);
        Pair<List<ShareOnlineVo>, Long> pair = shareOnlineService.findPageShareOnlineList(roomId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "黑名单列表")
    @GetMapping("/black/{roomId}")
    public OrcaResult<List<BlacklistUserVo>> blacklist(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        return OrcaResult.success(blacklistService.blacklist(roomId));
    }

    @Operation(summary = "移入黑名单")
    @PostMapping("/black/{roomId}/join")
    public OrcaResult<Void> joinBlackUser(@PathVariable("roomId") Long roomId,
                                          @Validated @RequestBody BlacklistUserRo ro) {
        checkRoom(roomId);
        blacklistService.joinUser(roomId, ro);
        return OrcaResult.success();
    }

    @Operation(summary = "移出黑名单")
    @PostMapping("/black/{roomId}/move")
    public OrcaResult<Void> moveBlackUser(@PathVariable("roomId") Long roomId,
                                          @Validated @RequestBody BlacklistUserRo ro) {
        checkRoom(roomId);
        blacklistService.removeUser(roomId, ro);
        return OrcaResult.success();
    }


    @Operation(summary = "禁言列表")
    @GetMapping("/mute/{roomId}")
    public OrcaResult<List<MuteUserVo>> muteUsers(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        return OrcaResult.success(muteService.muteList(roomId));
    }

    @Operation(summary = "移入禁言列表")
    @PostMapping("/mute/{roomId}/join")
    public OrcaResult<Void> joinMuteUser(@PathVariable("roomId") Long roomId,
                                         @Validated @RequestBody MuteUserRo ro) {
        checkRoom(roomId);
        muteService.joinUser(roomId, ro);
        return OrcaResult.success();
    }

    @Operation(summary = "移出禁言列表")
    @PostMapping("/mute/{roomId}/move")
    public OrcaResult<Void> moveMuteUser(@PathVariable("roomId") Long roomId,
                                         @Validated @RequestBody MuteUserRo ro) {
        checkRoom(roomId);
        muteService.removeUser(roomId, ro);
        return OrcaResult.success();
    }


    @Operation(summary = "全体禁言状态")
    @GetMapping("/all/mute/{roomId}")
    public OrcaResult<OperateAllMuteEnum> allMuteStatus(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        return OrcaResult.success(muteService.findAllMuteStatus(roomId));
    }

    @Operation(summary = "操作全体禁言状态")
    @PostMapping("/all/mute/{roomId}/{operate}")
    public OrcaResult<Void> operateAllMute(@PathVariable("roomId") Long roomId,
                                           @PathVariable("operate") OperateAllMuteEnum operate) {
        checkRoom(roomId);
        muteService.operateAllMute(roomId, operate);
        return OrcaResult.success();
    }


}
