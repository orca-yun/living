package ag.orca.living.core.dao.overview;


import ag.orca.living.core.entity.overview.LivingRoomStaticsRecord;
import ag.orca.living.core.vo.stats.LivingRoomStaticsItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingRoomStaticsRecordMapper {

    int insertOrUpdate(LivingRoomStaticsRecord record);

    List<LivingRoomStaticsRecord> findListByRoomIdAndDateBetween(@Param("roomId") Long roomId,
                                                                 @Param("roomRecordId") Long roomRecordId,
                                                                 @Param("start") LocalDateTime start,
                                                                 @Param("stop") LocalDateTime stop);

    List<LivingRoomStaticsItemVo> roomTrend(@Param("orgId") Long orgId,
                                            @Param("roomIds") List<Long> roomIds,
                                            @Param("start") LocalDateTime start,
                                            @Param("stop") LocalDateTime stop);

    Long maxOnlineNum(@Param("orgId") Long orgId,
                      @Param("roomIds") List<Long> roomIds,
                      @Param("start") LocalDateTime start,
                      @Param("stop") LocalDateTime stop);

    Long totalLiveUv(@Param("orgId") Long orgId);
}
