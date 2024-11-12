package ag.orca.living.api.channel;

import ag.orca.living.core.ro.query.QueryChannelDimensionRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionDetailRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionRo;
import ag.orca.living.core.ro.query.QueryLivingDimensionRo;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import ag.orca.living.core.vo.channel.ChannelRoomDetailVo;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

public interface ChannelDataService {


    Pair<List<ChannelDataDetailVo>, Long> findPageByLivingDimension(QueryLivingDimensionRo ro, Long orgId);

    ChannelDataBoardVo livingDimensionBoard(Long orgId, Long roomId);


    Pair<List<ChannelDataDetailVo>, Long> findPageByChannelDimension(QueryChannelDimensionRo ro, Long orgId);


    ChannelDataBoardVo channelDimensionBoard(Long orgId, Long channelId);


    Pair<List<ChannelDataDetailVo>, Long> findPageByTimeDimension(QueryChannelTimeDimensionRo ro, Long orgId);

    ChannelDataBoardVo timeDimensionBoard(Long orgId, LocalDate date);


    Pair<List<ChannelRoomDetailVo>, Long> timeDimensionRoomDetail(QueryChannelTimeDimensionDetailRo ro, Long orgId);
}
