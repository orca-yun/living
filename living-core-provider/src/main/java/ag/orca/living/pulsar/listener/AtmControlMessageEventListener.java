package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.ControlMessageEvent;
import ag.orca.living.core.repo.control.AtmosphereControlRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 氛围场控消息事件监听器
 *
 * 
 * @date 2024-05-07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AtmControlMessageEventListener implements MessageListener<ControlMessageEvent> {

    private final AtmosphereControlRepo atmosphereControlRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<ControlMessageEvent> consumer, Message<ControlMessageEvent> msg) {
        ControlMessageEvent event = msg.getValue();
        log.info("收到氛围场控消息, {}", event);

        // 发送氛围场控消息
        atmosphereControlRepo.sendMessage(event);

        consumer.acknowledge(msg);
    }
}
