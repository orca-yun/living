package ag.orca.living.controller;

import ag.orca.living.api.room.LivingRoomPermissionService;
import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.api.stats.OrgOverviewService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.convert.LivingRoomConvert;
import ag.orca.living.core.vo.room.LivingRoomExtPermissionVo;
import ag.orca.living.core.vo.room.LivingRoomPermissionVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.core.vo.stats.LivingOrgOverviewVo;
import ag.orca.living.core.vo.stats.LivingOrgWeekLiveNumVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/org/stats")
@Tag(name = "机构首页统计")
public class OrgLivingHomeDataController extends AbstractAdminController {
    @DubboReference
    OrgOverviewService orgOverviewService;

    @DubboReference
    LivingRoomService livingRoomService;

    @DubboReference
    LivingRoomPermissionService permissionService;

    @Operation(summary = "总共数据")
    @GetMapping("/overview")
    public OrcaResult<LivingOrgOverviewVo> overview() {
        Long orgId = getOrgId();
        return OrcaResult.success(orgOverviewService.livingOverview(orgId));
    }

    @Operation(summary = "最近一周开播次数统计")
    @GetMapping("/day")
    public OrcaResult<List<LivingOrgWeekLiveNumVo>> liveNumOverview() {
        Long orgId = getOrgId();
        List<LivingOrgWeekLiveNumVo> liveNumVos = orgOverviewService.weekLiveNumOverview(orgId);
        return OrcaResult.success(liveNumVos);
    }

    @Operation(summary = "今日开播房间列表")
    @GetMapping("/latest/room")
    public OrcaResult<List<LivingRoomExtPermissionVo>> latestRoom() {
        Long orgId = getOrgId();
        List<LivingRoomVo> roomVos = livingRoomService.findLatestTopLivingRoom(orgId, LocalDate.now());
        List<LivingRoomExtPermissionVo> roomExtVos = roomVos.stream().map(s -> {
            Optional<LivingRoomPermissionVo> optional = permissionService.findByRoomId(s.getId());
            return LivingRoomConvert.voToLivingRoomPermissionVo(s, optional);
        }).toList();
        return OrcaResult.success(roomExtVos);
    }


}
