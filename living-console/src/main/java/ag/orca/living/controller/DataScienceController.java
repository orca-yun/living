package ag.orca.living.controller;

import ag.orca.living.api.live.LivingLiveService;
import ag.orca.living.api.stats.RoomOverviewService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.vo.live.LivingRoomLiveRecordVo;
import ag.orca.living.core.vo.stats.LivingRoomConsoleStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomFinanceStaticsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@RestController
@RequestMapping("/v2/data")
@Tag(name = "数据服务")
public class DataScienceController extends AbstractConsoleController {


    @DubboReference
    RoomOverviewService roomOverviewService;

    @DubboReference
    LivingLiveService liveService;


    private Pair<LocalDateTime, LocalDateTime> queryTimeRange() {
        LocalDateTime stop = LocalDateTime.now();
        LocalDateTime start = stop.minusHours(6L);
        return Pair.of(start, stop);
    }

    @Operation(summary = "开播汇总数据")
    @GetMapping("/live/{roomId}")
    public OrcaResult<LivingRoomConsoleStaticsVo> liveStatics(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        LivingRoomConsoleStaticsVo vo = LivingRoomConsoleStaticsVo.builder().build();
        LivingRoomLiveRecordVo record = liveService.findLatestLiveRecord(roomId);
        if (Objects.nonNull(record)) {
            Pair<LocalDateTime, LocalDateTime> pair = queryTimeRange();
            vo = roomOverviewService.roomConsoleLiveStaticsData(roomId, record.getId(), pair);
        }
        return OrcaResult.success(vo);
    }


    @Operation(summary = "订单汇总数据")
    @GetMapping("/finance/{roomId}")
    public OrcaResult<LivingRoomFinanceStaticsVo> todayOrderStatics(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        LivingRoomFinanceStaticsVo vo = LivingRoomFinanceStaticsVo.builder().build();
        LivingRoomLiveRecordVo record = liveService.findLatestLiveRecord(roomId);
        if (Objects.nonNull(record)) {
            Pair<LocalDate, LocalDate> pair = Pair.of(LocalDate.now(), LocalDate.now());
            vo = roomOverviewService.roomConsoleOrderStaticsData(roomId, record.getId(), pair);
        }
        return OrcaResult.success(vo);
    }


}
