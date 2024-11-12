package ag.orca.living.provider.interact;

import ag.orca.living.api.interact.InteractBlacklistService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.bo.BlacklistUserInfo;
import ag.orca.living.core.convert.BlacklistInfoConvert;
import ag.orca.living.core.convert.MsgEventConvert;
import ag.orca.living.core.event.UserBlacklistEvent;
import ag.orca.living.core.repo.examine.ImExamineRepo;
import ag.orca.living.core.repo.interact.BlacklistRepo;
import ag.orca.living.core.ro.interact.BlacklistUserRo;
import ag.orca.living.core.vo.interact.BlacklistUserVo;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.pulsar.client.api.Producer;

import java.util.List;
import java.util.Objects;

@Slf4j
@DubboService
public class InteractBlacklistProvider implements InteractBlacklistService {

    @Resource
    BlacklistRepo blacklistRepo;

    @Resource
    ImExamineRepo imExamineRepo;

    @Resource
    Producer<UserBlacklistEvent> userBlacklistEventProducer;

    @Override
    public List<BlacklistUserVo> blacklist(Long roomId) {
        List<BlacklistUserInfo> infos = blacklistRepo.findAllBlacklist(roomId);
        return CommonConvert.map(infos, BlacklistInfoConvert::infoToVo);
    }

    @Override
    public void joinUser(Long roomId, BlacklistUserRo ro) {
        BlacklistUserInfo info = BlacklistInfoConvert.roToInfo(ro);
        if (Objects.nonNull(info)) {
            blacklistRepo.joinUserToBlacklist(roomId, info);
            imExamineRepo.deleteByRoomIdAndUid(roomId, info.getUid());
            UserBlacklistEvent event = MsgEventConvert.entityToUserBlacklistEvent(info, true);
            userBlacklistEventProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[黑名单事件] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
        }
    }

    @Override
    public void removeUser(Long roomId, BlacklistUserRo ro) {
        BlacklistUserInfo info = BlacklistInfoConvert.roToInfo(ro);
        if (Objects.nonNull(info)) {
            blacklistRepo.removeUserFromBlacklist(roomId, info);
        }
    }

    @Override
    public Boolean isInBlacklist(Long roomId, String uid) {
        return blacklistRepo.isInBlacklist(roomId, uid);
    }
}
