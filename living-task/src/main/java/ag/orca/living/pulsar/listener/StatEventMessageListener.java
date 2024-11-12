package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.ShareUserStatEvent;
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
public class StatEventMessageListener implements MessageListener<ShareUserStatEvent> {

    @Resource
    LiveViewRecordRepo liveViewRecordRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<ShareUserStatEvent> consumer, Message<ShareUserStatEvent> msg) {
        ShareUserStatEvent event = msg.getValue();
        log.info("进行统计 => {}", event);
        liveViewRecordRepo.statEvent(event);
        consumer.acknowledge(msg);
    }
}
