package ag.orca.living.api.stats;


import ag.orca.living.core.vo.stats.LivingRoomConsoleStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomFinanceStaticsVo;
import ag.orca.living.core.vo.stats.LivingRoomOverviewVo;
import ag.orca.living.core.vo.stats.LivingRoomStaticsItemVo;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomOverviewService {
    LivingRoomConsoleStaticsVo roomConsoleLiveStaticsData(Long roomId,
                                                          Long recordId,
                                                          Pair<LocalDateTime, LocalDateTime> pair);

    LivingRoomFinanceStaticsVo roomConsoleOrderStaticsData(Long roomId, Long id, Pair<LocalDate, LocalDate> pair);

    LivingRoomOverviewVo roomOverview(Long orgId, List<Long> roomIds,
                                      Pair<LocalDateTime, LocalDateTime> pair);

    List<LivingRoomStaticsItemVo> roomTrend(Long orgId, List<Long> roomIds,
                                            Pair<LocalDateTime, LocalDateTime> pair);


}
