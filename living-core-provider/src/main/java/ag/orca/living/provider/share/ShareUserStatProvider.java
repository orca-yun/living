package ag.orca.living.provider.share;

import ag.orca.living.api.share.ShareUserStatService;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.convert.ShareUserStatConvert;
import ag.orca.living.core.event.ShareUserStatEvent;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.pulsar.client.api.Producer;

import java.util.Optional;


@Slf4j
@DubboService
public class ShareUserStatProvider implements ShareUserStatService {

    @Resource
    Producer<ShareUserStatEvent> shareUserStatEventProducer;

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Override
    public void reportStat(AuthInfo authInfo, String ua) {
        Long roomId = authInfo.getRoomId();
        Optional<Long> optional = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
        optional.ifPresent(roomRecordId -> {
            ShareUserStatEvent event = ShareUserStatConvert.buildEvent(authInfo, roomRecordId, ua);
            shareUserStatEventProducer.sendAsync(event).thenAccept(messageId ->
                    log.info("[用户上报统计] roomId:{} msg: {}", event.getRoomId(), JsonUtil.beanToJson(event)));
        });
    }
}
