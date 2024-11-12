package ag.orca.living.provider.channel;

import ag.orca.living.api.channel.ChannelInfoService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ChannelInfoConvert;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.repo.channel.ChannelInfoRepo;
import ag.orca.living.core.ro.channel.ChannelInfoRo;
import ag.orca.living.core.ro.query.QueryChannelInfoRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;
import ag.orca.living.util.I18nUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@DubboService
public class ChannelInfoProvider implements ChannelInfoService {

    @Resource
    ChannelInfoRepo channelInfoRepo;

    private void checkDefChannel(Long channelId) {
        ChannelInfo info = channelInfoRepo.findFirstByChannelId(channelId);
        BoolEnum b = BoolEnum.ofCode(info.getSysInner());
        OrcaAssert.match(b == BoolEnum.TRUE, I18nUtil.getMessage("channel.default.not.change.err"));
    }

    @Override
    public void save(Long orgId, ChannelInfoRo ro) {
        ro.setChannelId(channelInfoRepo.randomChannelId());
        ChannelInfo entity = ChannelInfoConvert.roToCreateEntity(orgId, ro);
        if (Objects.nonNull(entity)) {
            channelInfoRepo.save(entity);
        }
    }


    @Override
    public void edit(ChannelInfoRo ro) {
        checkDefChannel(ro.getChannelId());
        ChannelInfo entity = ChannelInfoConvert.roToUpdateEntity(ro);
        if (Objects.nonNull(entity)) {
            channelInfoRepo.update(entity);
        }
    }

    @Override
    public void removeByChannelId(Long orgId, Long channelId) {
        checkDefChannel(channelId);
        channelInfoRepo.logicDel(orgId, channelId);
    }

    @Override
    public List<ChannelInfoVo> findAllChannelByOrgId(Long orgId) {
        List<ChannelInfo> list = channelInfoRepo.findAllChannelByOrgId(orgId);
        return CommonConvert.map(list, ChannelInfoConvert::entityToVo);
    }

    @Override
    public Pair<List<ChannelInfoVo>, Long> findPageList(QueryChannelInfoRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<ChannelInfo> channelInfos = channelInfoRepo.findListByOrgIdAndNameLike(ro.getOrgId(),
                ro.getChannelName(), ro.getCommissionRatio(), ro.getCommissionMethod(), ro.getCommissionPeriod());
        PageInfo<ChannelInfo> pageInfo = new PageInfo<>(channelInfos);
        List<ChannelInfoVo> vos = CommonConvert.map(pageInfo.getList(), ChannelInfoConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<ChannelInfoVo> findListByChannelIdList(List<Long> channelIds) {
        if (CollectionUtils.isEmpty(channelIds)) {
            return new ArrayList<>();
        }
        List<ChannelInfo> channelInfos = channelInfoRepo.findListByChannelIdIn(channelIds);
        return CommonConvert.map(channelInfos, ChannelInfoConvert::entityToVo);
    }

}
