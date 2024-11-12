package ag.orca.living.api.channel;

import ag.orca.living.core.ro.channel.ChannelInfoRo;
import ag.orca.living.core.ro.query.QueryChannelInfoRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface ChannelInfoService {

    /**
     * 新增渠道
     */
    void save(Long orgId, ChannelInfoRo ro);

    /**
     * 修改渠道
     */
    void edit(ChannelInfoRo ro);

    /**
     * 删除渠道
     */
    void removeByChannelId(Long orgId, Long channelId);


    List<ChannelInfoVo> findAllChannelByOrgId(Long orgId);

    /**
     * 渠道列表
     */
    Pair<List<ChannelInfoVo>, Long> findPageList(QueryChannelInfoRo ro);


    List<ChannelInfoVo> findListByChannelIdList(List<Long> channelIds);
}
