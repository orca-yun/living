package ag.orca.living.controller;

import ag.orca.living.api.interact.InteractShareOnlineService;
import ag.orca.living.api.media.LivingMediaLibService;
import ag.orca.living.api.room.LivingRoomPageService;
import ag.orca.living.api.room.LivingRoomPermissionService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.convert.LivingRoomConvert;
import ag.orca.living.core.ro.query.QueryRoomRo;
import ag.orca.living.core.ro.room.LivingRoomPwdRo;
import ag.orca.living.core.ro.room.LivingRoomRo;
import ag.orca.living.core.vo.media.LivingMediaLibVo;
import ag.orca.living.core.vo.room.LivingRoomExtVo;
import ag.orca.living.core.vo.room.LivingRoomPageVo;
import ag.orca.living.core.vo.room.LivingRoomPermissionVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/room/meta")
@Tag(name = "房间配置")
public class LivingRoomController extends AbstractAdminController {

    @DubboReference
    InteractShareOnlineService interactShareOnlineService;

    @DubboReference
    LivingRoomPageService pageService;

    @DubboReference
    LivingRoomPermissionService roomPermissionService;


    @DubboReference
    LivingMediaLibService mediaLibService;

    @Operation(summary = "房间列表")
    @GetMapping
    public OrcaPageResult<LivingRoomExtVo> roomList(QueryRoomRo ro) {
        Long orgId = getOrgId();
        Pair<List<LivingRoomVo>, Long> pair = livingRoomService.findPageList(orgId, ro);
        List<LivingRoomExtVo> roomExtVos = pair.getLeft().stream().map(s -> {
            Long t = interactShareOnlineService.totalShareOnline(s.getId());
            Optional<LivingMediaLibVo> mediaOptional =
                    Objects.nonNull(s.getMediaBizId()) ? mediaLibService.findFirstById(s.getMediaBizId()) : Optional.empty();
            Optional<LivingRoomPageVo> pageOptional = pageService.findByRoomId(s.getId());
            Optional<LivingRoomPermissionVo> permissionOptional = roomPermissionService.findByRoomId(s.getId());
            return LivingRoomConvert.voToLivingRoomExtVo(s, t, mediaOptional, pageOptional, permissionOptional);
        }).toList();
        return OrcaPageResult.success(pair.getRight(), roomExtVos);
    }

    @Operation(summary = "创建房间")
    @PostMapping
    public OrcaResult<Void> saveRoom(@Validated @RequestBody LivingRoomRo ro) {
        checkAccountBalance();
        ro.setOrgId(getOrgId());
        livingRoomService.save(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "更新房间")
    @PutMapping
    public OrcaResult<Void> editRoom(@Validated @RequestBody LivingRoomRo ro) {
        livingRoomService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除房间")
    @DeleteMapping("/{roomId}")
    public OrcaResult<Void> removeRoom(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        Long orgId = getOrgId();
        livingRoomService.remove(orgId, Collections.singletonList(roomId));
        return OrcaResult.success();
    }


    @Operation(summary = "房间密码修改")
    @PutMapping("/pwd/{roomId}")
    public OrcaResult<Void> editRoomPwd(@PathVariable("roomId") Long roomId,
                                        @Validated @RequestBody LivingRoomPwdRo ro) {
        checkRoom(roomId);
        OrcaAssert.match(!Objects.equals(roomId, ro.getRoomId()), I18nUtil.getMessage("room.error"));
        livingRoomService.modRoomPwd(roomId, ro);
        return OrcaResult.success();
    }


}
