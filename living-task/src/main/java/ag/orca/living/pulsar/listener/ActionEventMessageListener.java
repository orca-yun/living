package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.ShareUserActionEvent;
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
public class ActionEventMessageListener implements MessageListener<ShareUserActionEvent> {

    @Resource
    LiveViewRecordRepo liveViewRecordRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<ShareUserActionEvent> consumer, Message<ShareUserActionEvent> msg) {

        ShareUserActionEvent event = msg.getValue();
        log.info("观看端上下线行为 => {}", event);
        liveViewRecordRepo.eventAction(event);
        consumer.acknowledge(msg);
    }
}
