package ag.orca.living.pulsar;

import ag.orca.living.core.event.VideoStreamTaskEvent;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ProducerConfig {
    @Bean
    public Producer<VideoStreamTaskEvent> videoStreamTaskEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(VideoStreamTaskEvent.class))
                .topic("video.stream.task.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }
}
