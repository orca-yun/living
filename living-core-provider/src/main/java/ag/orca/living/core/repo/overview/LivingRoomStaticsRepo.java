package ag.orca.living.core.repo.overview;

import ag.orca.living.core.dao.order.OrderInfoMapper;
import ag.orca.living.core.dao.overview.LivingRoomStaticsRecordMapper;
import ag.orca.living.core.dao.share.LivingShareUserViewRecordMapper;
import ag.orca.living.core.entity.overview.LivingRoomStaticsRecord;
import ag.orca.living.core.vo.stats.LivingRoomFinanceStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomOverviewVo;
import ag.orca.living.core.vo.stats.LivingRoomStaticsItemVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class LivingRoomStaticsRepo {

    @Resource
    LivingRoomStaticsRecordMapper roomStaticsRecordMapper;

    @Resource
    LivingShareUserViewRecordMapper livingShareUserViewRecordMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;


    public List<LivingRoomStaticsRecord> findListByRoomIdAndDateBetween(Long roomId, Long recordId, LocalDateTime start, LocalDateTime stop) {
        return roomStaticsRecordMapper.findListByRoomIdAndDateBetween(roomId, recordId, start, stop);
    }

    public List<LivingRoomStaticsItemVo> roomTrend(Long orgId,
                                                   List<Long> roomIds,
                                                   LocalDateTime start,
                                                   LocalDateTime stop) {
        return roomStaticsRecordMapper.roomTrend(orgId, roomIds, start, stop);
    }

    public LivingRoomOverviewVo roomOverview(Long orgId, List<Long> roomIds, LocalDateTime start, LocalDateTime stop) {
        LivingRoomOverviewVo vo = livingShareUserViewRecordMapper.roomOverviewStatics(orgId, roomIds, start.toLocalDate(), stop.toLocalDate());
        Long maxOnlineNum = roomStaticsRecordMapper.maxOnlineNum(orgId, roomIds, start, stop);
        vo.setOnlineNum(Objects.isNull(maxOnlineNum) ? 0L : maxOnlineNum);
        return vo;
    }

    public Optional<Long> totalLiveUv(Long orgId) {
        return Optional.ofNullable(roomStaticsRecordMapper.totalLiveUv(orgId));
    }

    public LivingRoomFinanceStaticsVo roomOrderFinanceStaticsData(Long roomId, LocalDate start, LocalDate stop) {
        return orderInfoMapper.roomOrderFinanceStatics(roomId, start, stop);
    }
}
