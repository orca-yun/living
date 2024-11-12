package ag.orca.living.provider.stats;

import ag.orca.living.api.stats.RoomOverviewService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomStaticsRecordConvert;
import ag.orca.living.core.entity.overview.LivingRoomStaticsRecord;
import ag.orca.living.core.repo.overview.LivingRoomStaticsRepo;
import ag.orca.living.core.vo.stats.LivingRoomConsoleStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomFinanceStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomOverviewVo;
import ag.orca.living.core.vo.stats.LivingRoomStaticsItemVo;
import ag.orca.living.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@DubboService
public class RoomOverviewProvider implements RoomOverviewService {


    @Resource
    LivingRoomStaticsRepo roomStaticsRepo;

    @Override
    public LivingRoomConsoleStaticsVo roomConsoleLiveStaticsData(Long roomId, Long recordId, Pair<LocalDateTime, LocalDateTime> pair) {
        LivingRoomConsoleStaticsVo vo = LivingRoomConsoleStaticsVo.builder().build();
        List<LivingRoomStaticsRecord> records = roomStaticsRepo.findListByRoomIdAndDateBetween(roomId, recordId, pair.getLeft(), pair.getRight());
        vo.setRecords(CommonConvert.map(records, LivingRoomStaticsRecordConvert::entityToVo));
        Optional<LivingRoomStaticsRecord> latest = records.stream().max(Comparator.comparing(LivingRoomStaticsRecord::getId));
        latest.ifPresent(p -> {
            vo.setPv(p.getViewPageNum());
            vo.setUv(p.getOfflineNum() + vo.getOnlineNum());
            vo.setOnlineNum(p.getOnlineNum());
        });
        return vo;
    }

    @Override
    public LivingRoomFinanceStaticsVo roomConsoleOrderStaticsData(Long roomId, Long recordId, Pair<LocalDate, LocalDate> pair) {
        LocalDate start = pair.getLeft();
        LocalDate stop = pair.getRight();
        return roomStaticsRepo.roomOrderFinanceStaticsData(roomId, start, stop);
    }


    @Override
    public LivingRoomOverviewVo roomOverview(Long orgId, List<Long> roomIds, Pair<LocalDateTime, LocalDateTime> pair) {
        LocalDateTime start = pair.getLeft();
        LocalDateTime stop = pair.getRight();
        return roomStaticsRepo.roomOverview(orgId, roomIds, start, stop);
    }

    @Override
    public List<LivingRoomStaticsItemVo> roomTrend(Long orgId, List<Long> roomIds, Pair<LocalDateTime, LocalDateTime> pair) {
        LocalDateTime start = pair.getLeft();
        LocalDateTime stop = pair.getRight();
        List<LivingRoomStaticsItemVo> itemVos = roomStaticsRepo.roomTrend(orgId, roomIds, start, stop);
        return DateUtil.dateTimeRangeFunc(start, stop, Pair.of(ChronoUnit.MINUTES, 1L), ldr -> {
            LivingRoomStaticsItemVo v = LivingRoomStaticsItemVo.builder().statTime(ldr).build();
            if (CollectionUtils.isNotEmpty(itemVos)) {
                return itemVos.stream().filter(s -> s.getStatTime().equals(ldr))
                        .findFirst().orElse(v);
            }
            return v;
        });
    }
}
