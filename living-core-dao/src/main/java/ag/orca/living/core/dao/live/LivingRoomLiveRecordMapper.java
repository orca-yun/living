package ag.orca.living.core.dao.live;

import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.vo.stats.LivingOrgWeekLiveNumVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;


@Mapper
public interface LivingRoomLiveRecordMapper {
    int insert(LivingRoomLiveRecord record);

    LivingRoomLiveRecord findLatestLiveRecord(@Param("roomId") Long roomId);

    int finishLivingRecord(LivingRoomLiveRecord record);

    List<LivingRoomLiveRecord> findListByRecordIdList(@Param("ids") List<Long> ids, @Param("status") Integer status);

    LivingRoomLiveRecord findLiveRecordById(Long id);

    List<LivingRoomLiveRecord> findListByNearRoomIdList(@Param("roomIds") List<Long> roomIds);

    List<LivingOrgWeekLiveNumVo> weekLiveNum(@Param("orgId") Long orgId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("stopDate") LocalDate stopDate);

    Long totalLiveNum(@Param("orgId") Long orgId);

    List<LivingRoomLiveRecord> findLivingRecordsByOrgIdAndStartDate(@Param("orgId") Long orgId,
                                                                    @Param("startDate") LocalDate startDate);


    List<LivingRoomLiveRecord> findLiveRecordsByRoomIdAndStartDate(@Param("roomId") Long roomId,
                                                                   @Param("startDate") LocalDate startDate);
}
