package ag.orca.living.provider.stream;

import ag.orca.living.api.stream.LivingStreamService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.repo.channel.ChannelInfoRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.stream.StreamManageRepo;
import ag.orca.living.core.vo.stream.LivingE2eAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPullAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPushAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamTransPullAddressVo;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class LivingStreamProvider implements LivingStreamService {

    @Resource
    StreamManageRepo streamManageRepo;

    @Resource
    LivingRoomRepo roomRepo;

    @Resource
    ChannelInfoRepo channelInfoRepo;

    @Override
    public LivingStreamTransPullAddressVo buildTransPullAddress(Long roomId) {
        return streamManageRepo.buildTransPullAddress(roomId);
    }

    @Override
    public LivingStreamPullAddressVo buildPullAddress(Long roomId) {
        Optional<LivingRoom> optional = roomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        return streamManageRepo.buildPullAddress(roomId);
    }

    @Override
    public LivingStreamPushAddressVo buildPushAddress(Long roomId) {
        return streamManageRepo.buildPushAddress(roomId);
    }

    @Override
    public LivingE2eAddressVo buildE2eAddress(Long orgId, Long roomId, Long channelId) {
        if (Objects.isNull(channelId)) {
            channelId = channelInfoRepo.findDefaultChannelId(orgId);
        }
        return streamManageRepo.buildE2eAddress(orgId, roomId, channelId);
    }


}
