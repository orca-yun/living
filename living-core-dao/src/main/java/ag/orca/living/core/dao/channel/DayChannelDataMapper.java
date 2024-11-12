package ag.orca.living.core.dao.channel;

import ag.orca.living.core.entity.channel.DayChannelData;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DayChannelDataMapper {

    int batchInsertOrUpdate(@Param("items") List<DayChannelData> dayChannelData);


    ChannelDataBoardVo channelDimensionBoard(@Param("orgId") Long orgId, @Param("channelId") Long channelId);


    List<ChannelDataDetailVo> findPageByChannelDimension(@Param("orgId") Long orgId,
                                                         @Param("channelId") Long channelId,
                                                         @Param("flowSort") Integer flowSort,
                                                         @Param("amountSort") Integer amountSort);


    ChannelDataBoardVo timeDimensionBoard(@Param("orgId") Long orgId, @Param("date") LocalDate date);

    List<ChannelDataDetailVo> findPageByTimeDimension(@Param("orgId") Long orgId,
                                                      @Param("date") LocalDate date,
                                                      @Param("flowSort") Integer flowSort,
                                                      @Param("amountSort") Integer amountSort);

}



