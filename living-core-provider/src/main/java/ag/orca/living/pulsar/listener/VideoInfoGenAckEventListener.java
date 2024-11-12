package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.VideInfoGenAckEvent;
import ag.orca.living.core.repo.media.LivingMediaLibRepo;
import ag.orca.living.core.repo.storage.ObjectStorageRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class VideoInfoGenAckEventListener implements MessageListener<VideInfoGenAckEvent> {

    @Resource
    LivingMediaLibRepo livingMediaLibRepo;

    @Resource
    ObjectStorageRepo objectStorageRepo;

    @SneakyThrows
    @Override
    public void received(Consumer<VideInfoGenAckEvent> consumer, Message<VideInfoGenAckEvent> msg) {
        VideInfoGenAckEvent event = msg.getValue();
        log.info("视频信息生成结果 => {}", event);
        String bucket = event.getBucket();
        String key = event.getKey();
        String accessUrl = objectStorageRepo.imgPath(bucket, key);
        livingMediaLibRepo.renderImageInfo(event.getMediaId(), accessUrl, event.getDuration(), event.getCapacity());
        consumer.acknowledge(msg);

    }
}
