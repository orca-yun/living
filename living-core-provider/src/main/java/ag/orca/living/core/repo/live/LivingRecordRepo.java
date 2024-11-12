package ag.orca.living.core.repo.live;

import ag.orca.living.core.dao.live.LivingRoomLiveRecordMapper;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.vo.stats.LivingOrgWeekLiveNumVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class LivingRecordRepo {

    @Resource
    LivingRoomLiveRecordMapper roomLiveRecordMapper;


    public List<LivingOrgWeekLiveNumVo> weekLiveNum(Long orgId, LocalDate start, LocalDate stop) {
        return roomLiveRecordMapper.weekLiveNum(orgId, start, stop);
    }

    public Optional<Long> totalLiveNum(Long orgId) {
        return Optional.ofNullable(roomLiveRecordMapper.totalLiveNum(orgId));
    }

    public List<LivingRoomLiveRecord> findLivingRecordsByOrgIdAndStartDate(Long orgId,
                                                                           LocalDate startDate) {
        return roomLiveRecordMapper.findLivingRecordsByOrgIdAndStartDate(orgId, startDate);
    }
}
