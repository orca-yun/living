package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.UserSendStatEvent;
import ag.orca.living.core.repo.LiveViewRecordRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserSendStatMessageListener implements MessageListener<UserSendStatEvent> {

    @Resource
    LiveViewRecordRepo liveViewRecordRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<UserSendStatEvent> consumer, Message<UserSendStatEvent> msg) {
        UserSendStatEvent event = msg.getValue();
        log.info("用户发送次数统计 => {}", event);
        liveViewRecordRepo.userSendStat(event);
        consumer.acknowledge(msg);
    }
}
