package ag.orca.living.core.repo.channel;

import ag.orca.living.core.convert.ChannelInfoConvert;
import ag.orca.living.core.dao.channel.ChannelInfoMapper;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class ChannelInfoRepo {


    @Resource
    ChannelInfoMapper channelInfoMapper;

    public Long randomChannelId() {
        long channelId = RandomUtil.randomId();
        return channelInfoMapper.existsChannelId(channelId) <= 0 ? channelId : randomChannelId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ChannelInfo channelInfo) {
        channelInfoMapper.insert(channelInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ChannelInfo channelInfo) {
        if (Objects.nonNull(channelInfo.getCommissionRatio())) {
            //修改渠道的分佣比例,隔天定时修改生效
            channelInfo.setPrepareCommissionRatio(channelInfo.getCommissionRatio());
        }
        channelInfoMapper.update(channelInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void logicDel(Long orgId, Long channelId) {
        channelInfoMapper.logicDel(orgId, channelId, LocalDateTime.now());
    }

    public List<ChannelInfo> findListByOrgIdAndNameLike(Long orgId,
                                                        String name,
                                                        Integer ratio,
                                                        Integer method,
                                                        Integer period) {
        return channelInfoMapper.findListByOrgIdAndNameLike(orgId, name, ratio, method, period);
    }

    public List<ChannelInfo> findAllChannelByOrgId(Long orgId) {
        return channelInfoMapper.findAllChannelByOrgId(orgId);
    }

    public ChannelInfo findFirstByChannelId(Long channelId) {
        return channelInfoMapper.findFirstByChannelId(channelId);
    }

    public List<ChannelInfo> findListByChannelIdIn(List<Long> channelIds) {
        return channelInfoMapper.findListByChannelIdIn(channelIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long bindDefaultChannel(Long orgId, String channelName) {
        Long channelId = randomChannelId();
        save(ChannelInfoConvert.buildDefaultChannel(orgId, channelName, channelId));
        return channelId;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long findDefaultChannelId(Long orgId) {
        Long defaultChannelId = channelInfoMapper.findDefaultChannelId(orgId);
        if (Objects.isNull(defaultChannelId)) {
            return bindDefaultChannel(orgId, "默认渠道");
        }
        return defaultChannelId;
    }

}
