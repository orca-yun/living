package ag.orca.living.controller;

import ag.orca.living.api.stats.RoomOverviewService;
import ag.orca.living.common.OrcaConst;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.query.QueryRoomOverviewRo;
import ag.orca.living.core.vo.stats.LivingRoomOverviewVo;
import ag.orca.living.core.vo.stats.LivingRoomStaticsItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/overview")
@Tag(name = "直播数据")
public class LivingOverviewController extends AbstractAdminController {


    @DubboReference
    RoomOverviewService roomOverviewService;

    private Pair<LocalDateTime, LocalDateTime> queryTimeRange() {
        LocalDateTime stop = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(stop.toLocalDate(), OrcaConst.ZERO_TIME);
        return Pair.of(start, stop);
    }

    @Operation(summary = "直播数据")
    @GetMapping("/data")
    public OrcaResult<LivingRoomOverviewVo> roomOverview(@Validated QueryRoomOverviewRo ro) {
        Long orgId = getOrgId();
        LivingRoomOverviewVo vo = roomOverviewService.roomOverview(orgId, ro.getRoomIds(), queryTimeRange());
        return OrcaResult.success(vo);
    }

    @Operation(summary = "直播趋势")
    @GetMapping("/trend")
    public OrcaResult<List<LivingRoomStaticsItemVo>> roomTrend(@Validated QueryRoomOverviewRo ro) {
        Long orgId = getOrgId();
        List<LivingRoomStaticsItemVo> vos = roomOverviewService.roomTrend(orgId, ro.getRoomIds(), queryTimeRange());
        return OrcaResult.success(vos);
    }
}
