package ag.orca.living.core.dao.channel;

import ag.orca.living.core.entity.channel.DayLiveRoomChannelData;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DayLivingRoomChannelDataMapper {


    int batchInsertOrUpdate(@Param("items") List<DayLiveRoomChannelData> dayLiveRoomChannelData);

    ChannelDataBoardVo livingDimensionBoard(@Param("orgId") Long orgId, @Param("roomId") Long roomId);

    List<ChannelDataDetailVo> findPageByLivingDimension(@Param("orgId") Long orgId,
                                                        @Param("roomId") Long roomId,
                                                        @Param("flowSort") Integer flowSort,
                                                        @Param("amountSort") Integer amountSort);

    List<DayLiveRoomChannelData> findPageByTimeDimensionRoomDetail(@Param("channelId") Long channelId,
                                                                   @Param("date") LocalDate date);


}
