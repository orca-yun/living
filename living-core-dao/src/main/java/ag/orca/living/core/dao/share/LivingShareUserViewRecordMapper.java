package ag.orca.living.core.dao.share;

import ag.orca.living.core.entity.share.LivingShareUserViewRecord;
import ag.orca.living.core.vo.stats.LivingRoomOverviewVo;
import ag.orca.living.core.vo.stats.LivingRoomShareUserStaticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface LivingShareUserViewRecordMapper {

    int insertOrUpdate(LivingShareUserViewRecord record);

    List<LivingShareUserViewRecord> findListByRoomIdAndViewDateAndChannelIds(
            @Param("roomId") Long roomId,
            @Param("viewDate") LocalDate viewDate,
            @Param("channelIds") List<Long> channelIds);


    /**
     * 房间统计给到 运营端 数据展览
     *
     * @param orgId
     * @param roomIds
     * @param start
     * @param stop
     * @return
     */
    LivingRoomOverviewVo roomOverviewStatics(@Param("orgId") Long orgId,
                                             @Param("roomIds") List<Long> roomIds,
                                             @Param("start") LocalDate start,
                                             @Param("stop") LocalDate stop);


    LivingRoomShareUserStaticsVo roomShareUserStatics(@Param("roomId") Long roomId,
                                                      @Param("start") LocalDate start,
                                                      @Param("stop") LocalDate stop);

    Long countUserCntByOrgIdAndViewDate(@Param("orgId") Long orgId, @Param("viewDate") LocalDate viewDate);
}
