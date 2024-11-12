package ag.orca.living.pulsar;

import ag.orca.living.core.event.ShareUserActionEvent;
import ag.orca.living.core.event.ShareUserStatEvent;
import ag.orca.living.core.event.UserSendStatEvent;
import ag.orca.living.pulsar.listener.ActionEventMessageListener;
import ag.orca.living.pulsar.listener.StatEventMessageListener;
import ag.orca.living.pulsar.listener.UserSendStatMessageListener;
import jakarta.annotation.Resource;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ConsumerConfig {

    @Resource
    StatEventMessageListener statEventMessageListener;

    @Resource
    ActionEventMessageListener actionEventMessageListener;

    @Resource
    UserSendStatMessageListener userSendStatMessageListener;

    @Bean
    public Consumer<ShareUserStatEvent> shareUserStatEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(ShareUserStatEvent.class))
                .subscriptionName("statEvent")
                .subscriptionType(SubscriptionType.Shared)
                .topic("stat.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(statEventMessageListener)
                .subscribe();
    }


    @Bean
    public Consumer<ShareUserActionEvent> actionEventMessageListenerConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(ShareUserActionEvent.class))
                .subscriptionName("actionEvent")
                .subscriptionType(SubscriptionType.Shared)
                .topic("share.user.action.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(actionEventMessageListener)
                .subscribe();
    }


    @Bean
    public Consumer<UserSendStatEvent> userSendStatEventConsumer(PulsarClient client) throws PulsarClientException {
        return client.newConsumer(Schema.JSON(UserSendStatEvent.class))
                .subscriptionName("userSendStatEvent")
                .subscriptionType(SubscriptionType.Shared)
                .topic("user.send.stat.event")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Latest)
                .autoUpdatePartitions(true)
                .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)
                .messageListener(userSendStatMessageListener)
                .subscribe();
    }


}
