package ag.orca.living.core.dao.channel;

import ag.orca.living.core.entity.channel.ChannelInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChannelInfoMapper {

    int existsChannelId(@Param("channelId") Long channelId);

    int insert(ChannelInfo record);

    int update(ChannelInfo record);

    int logicDel(@Param("orgId") Long orgId,
                 @Param("channelId") Long channelId,
                 @Param("dateTime") LocalDateTime dateTime);

    ChannelInfo findFirstByChannelId(@Param("channelId") Long channelId);

    List<ChannelInfo> findListByChannelIdIn(@Param("channelIds") List<Long> channelIds);

    List<ChannelInfo> findListByOrgIdAndNameLike(@Param("orgId") Long orgId,
                                                 @Param("channelName") String channelName,
                                                 @Param("commissionRatio") Integer commissionRatio,
                                                 @Param("commissionMethod") Integer commissionMethod,
                                                 @Param("commissionPeriod") Integer commissionPeriod);

    List<ChannelInfo> findAllChannelByOrgId(@Param("orgId") Long orgId);

    Long findDefaultChannelId(@Param("orgId") Long orgId);

    void updateCommissionRatio(@Param("dateTime") LocalDateTime dateTime);


}
