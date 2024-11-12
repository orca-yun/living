package ag.orca.living.provider.share;

import ag.orca.living.api.share.LivingShareUserViewRecordService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingShareUserViewRecordConvert;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.share.LivingShareUserViewRecord;
import ag.orca.living.core.repo.channel.ChannelInfoRepo;
import ag.orca.living.core.repo.share.LivingShareUserViewRecordRepo;
import ag.orca.living.core.ro.query.QueryShareUserRo;
import ag.orca.living.core.vo.share.LivingShareUserViewRecordVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@DubboService
public class LivingShareUserViewRecordProvider implements LivingShareUserViewRecordService {

    @Resource
    LivingShareUserViewRecordRepo shareUserViewRecordRepo;

    @Resource
    ChannelInfoRepo channelInfoRepo;

    @Override
    public Pair<List<LivingShareUserViewRecordVo>, Long> findPageList(Long orgId, QueryShareUserRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<LivingShareUserViewRecord> records = shareUserViewRecordRepo.findListByRoomIdAndViewDateAndChannelIds(
                ro.getRoomId(), LocalDate.now(), ro.getChannelIds());
        PageInfo<LivingShareUserViewRecord> pageInfo = new PageInfo<>(records);
        List<ChannelInfo> channelInfos = channelInfoRepo.findAllChannelByOrgId(orgId);
        List<LivingShareUserViewRecordVo> vos = CommonConvert.map(pageInfo.getList(), s -> LivingShareUserViewRecordConvert.entityToVo(s, channelInfos));
        return Pair.of(vos, pageInfo.getTotal());
    }
}
