package ag.orca.living.core.repo.channel;

import ag.orca.living.core.dao.channel.DayChannelDataMapper;
import ag.orca.living.core.dao.channel.DayLivingRoomChannelDataMapper;
import ag.orca.living.core.entity.channel.DayLiveRoomChannelData;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
public class ChannelDataRepo {

    @Resource
    DayChannelDataMapper dayChannelDataMapper;

    @Resource
    DayLivingRoomChannelDataMapper dayLivingRoomChannelDataMapper;

    public ChannelDataBoardVo livingDimensionBoard(Long orgId, Long roomId) {
        return dayLivingRoomChannelDataMapper.livingDimensionBoard(orgId, roomId);
    }

    public List<ChannelDataDetailVo> findPageByLivingDimension(Long orgId, Long roomId, Integer flowSort, Integer amountSort) {
        return dayLivingRoomChannelDataMapper.findPageByLivingDimension(orgId, roomId, flowSort, amountSort);
    }

    public ChannelDataBoardVo channelDimensionBoard(Long orgId, Long channelId) {
        return dayChannelDataMapper.channelDimensionBoard(orgId, channelId);
    }

    public List<ChannelDataDetailVo> findPageByChannelDimension(Long orgId, Long channelId, Integer flowSort, Integer amountSort) {
        return dayChannelDataMapper.findPageByChannelDimension(orgId, channelId, flowSort, amountSort);
    }

    public ChannelDataBoardVo timeDimensionBoard(Long orgId, LocalDate date) {
        return dayChannelDataMapper.timeDimensionBoard(orgId, date);
    }

    public List<ChannelDataDetailVo> findPageByTimeDimension(Long orgId, LocalDate date, Integer flowSort, Integer amountSort) {
        return dayChannelDataMapper.findPageByTimeDimension(orgId, date, flowSort, amountSort);
    }

    public List<DayLiveRoomChannelData> findPageByTimeDimensionRoomDetail(Long channelId, LocalDate date) {
        return dayLivingRoomChannelDataMapper.findPageByTimeDimensionRoomDetail(channelId, date);
    }

}
