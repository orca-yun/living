package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.VideoInfoConvertAckEvent;
import ag.orca.living.core.repo.media.LivingMediaLibRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VideoInfoConvertAckEventListener implements MessageListener<VideoInfoConvertAckEvent> {

    @Resource
    LivingMediaLibRepo livingMediaLibRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<VideoInfoConvertAckEvent> consumer, Message<VideoInfoConvertAckEvent> msg) {
        VideoInfoConvertAckEvent event = msg.getValue();
        log.info("视频转换生成结果 => {}", event);
        livingMediaLibRepo.renderConvertPathName(event.getMediaId(), event.getKey(), event.getConvertStatus());
        consumer.acknowledge(msg);

    }
}
