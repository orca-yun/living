package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.VideoStreamTaskEvent;
import ag.orca.living.core.repo.live.LivingVideoRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VideoStreamTaskEventMessageListener implements MessageListener<VideoStreamTaskEvent> {

    @Resource
    LivingVideoRepo livingVideoRepo;


    @SneakyThrows
    @Override
    public void received(Consumer<VideoStreamTaskEvent> consumer, Message<VideoStreamTaskEvent> msg) {
        VideoStreamTaskEvent event = msg.getValue();
        log.info("视频任务信开始开启直播 => {}", event);
        livingVideoRepo.videoOperateLiveStart(event.getRoomId(), event.getMediaId(), event.getTimes(), event.getTargetDuration(), event.getLiveSource());
        consumer.acknowledge(msg);
    }
}
