package ag.orca.living.pulsar;

import ag.orca.living.core.event.*;
import ag.orca.living.pulsar.listener.*;
import jakarta.annotation.Resource;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ConsumerConfig {

    @Resource
    CoursewareConvertEventMessageListener coursewareConvertEventMessageListener;

    @Resource
    VideoPushTaskStatusEventMessageListener videoPushTaskStatusEventMessageListener;

    @Resource
    VideoInfoGenAckEventListener videoInfoGenAckEventListener;

    @Resource
    VideoStreamTaskEventMessageListener videoStreamTaskEventMessageListener;

    @Resource
    VideoInfoConvertAckEventListener videoInfoConvertAckEventListener;

    @Bean
    public Consumer<CoursewareConvertEvent> coursewareConvertEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(CoursewareConvertEvent.class))
                .subscriptionName("courseware-convert")
                .subscriptionType(SubscriptionType.Shared)
                .topic("courseware.convert.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(coursewareConvertEventMessageListener)
                .subscribe();
    }

    @Bean
    public Consumer<VideoPushTaskStatusEvent> videoPushTaskStatusEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(VideoPushTaskStatusEvent.class))
                .subscriptionName("video-push-task-status")
                .subscriptionType(SubscriptionType.Shared)
                .topic("video.push.task.status.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(videoPushTaskStatusEventMessageListener)
                .subscribe();
    }

    @Bean
    public Consumer<VideoStreamTaskEvent> videoStreamTaskEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(VideoStreamTaskEvent.class))
                .subscriptionName("video-stream-task-event")
                .subscriptionType(SubscriptionType.Shared)
                .topic("video.stream.task.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(videoStreamTaskEventMessageListener)
                .subscribe();
    }


    @Bean
    public Consumer<VideoInfoConvertAckEvent> videoInfoConvertAckEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(VideoInfoConvertAckEvent.class))
                .subscriptionName("video-info-convert-ack")
                .subscriptionType(SubscriptionType.Shared)
                .topic("video.info.convert.ack.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(videoInfoConvertAckEventListener)
                .subscribe();
    }


    @Bean
    public Consumer<VideInfoGenAckEvent> videInfoGenAckEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(VideInfoGenAckEvent.class))
                .subscriptionName("video-info-gen-ack")
                .subscriptionType(SubscriptionType.Shared)
                .topic("video.info.gen.ack.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(videoInfoGenAckEventListener)
                .subscribe();
    }

    @Bean
    public Consumer<ControlMessageEvent> atmControlMessageEventConsumer(PulsarClient client,
                                                                        AtmControlMessageEventListener listener)
            throws PulsarClientException {
        return client.newConsumer(Schema.JSON(ControlMessageEvent.class))
                .subscriptionName("atmControlMessageEvent")
                .subscriptionType(SubscriptionType.Shared)
                .topic("atm.control.message.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(listener)
                .subscribe();
    }

}
