package ag.orca.living.pulsar;

import ag.orca.living.core.event.*;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class ProducerConfig {


    /**
     * 审核结果消息 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<ImExamineEvent> imExamineProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(ImExamineEvent.class))
                .topic("bc.im.examine.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 商品推荐消息 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<GoodsActionEvent> goodsActionProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(GoodsActionEvent.class))
                .topic("bc.goods.action.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 商品推荐消息 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<GiftActionEvent> giftActionProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(GiftActionEvent.class))
                .topic("bc.gift.action.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 商品推荐消息 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<GiftSendEvent> giftSendProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(GiftSendEvent.class))
                .topic("bc.gift.send.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }

    /**
     * 放假开播状态通知 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<LiveStatusNotifyEvent> liveStatusNotifyEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(LiveStatusNotifyEvent.class))
                .topic("bc.live.status.notify.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 消息回复指令 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<SendReplyMsgEvent> sendReplyMsgEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(SendReplyMsgEvent.class))
                .topic("bc.send.reply.msg.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 消息撤回指令 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<RollbackMsgEvent> rollbackMsgEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(RollbackMsgEvent.class))
                .topic("bc.rollback.msg.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 用户加入黑名单指令 其他Fire节点需要消费
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<UserBlacklistEvent> userBlacklistEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(UserBlacklistEvent.class))
                .topic("bc.user.blacklist.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 统计上报 Topic 其他节点都不需要消费
     *
     * @param client Pulsar Client
     * @return
     * @throws PulsarClientException 异常
     */
    @Bean
    public Producer<ShareUserStatEvent> shareUserStatEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(ShareUserStatEvent.class))
                .topic("stat.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }

    /**
     * 发送用户事件
     *
     * @param client
     * @return
     * @throws PulsarClientException
     */
    @Bean
    public Producer<UserSendStatEvent> userSendStatEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(UserSendStatEvent.class))
                .topic("user.send.stat.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * 课件转换 Topic  其他节点不需要消费
     *
     * @param client Pulsar Client
     * @return
     * @throws PulsarClientException 异常
     */
    @Bean
    public Producer<CoursewareConvertEvent> coursewareConvertEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(CoursewareConvertEvent.class))
                .topic("courseware.convert.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }

    /**
     * 媒体文件触发推流 Topic  其他节点不需要消费
     *
     * @param client Pulsar Client
     * @throws PulsarClientException 异常
     */
    @Bean
    public Producer<VideoStartLiveEvent> videoStartLiveEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(VideoStartLiveEvent.class))
                .topic("video.start.live.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * stop Topic  其他节点不需要消费
     *
     * @param client Pulsar Client
     * @throws PulsarClientException 异常
     */
    @Bean
    public Producer<VideoStopLiveEvent> videoStopLiveEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(VideoStopLiveEvent.class))
                .topic("bc.video.stop.live.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }


    /**
     * stop Topic  其他节点不需要消费
     *
     * @param client Pulsar Client
     * @throws PulsarClientException 异常
     */
    @Bean
    public Producer<VideoInfoGenEvent> videoInfoGenEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(VideoInfoGenEvent.class))
                .topic("video.info.gen.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }

    @Bean
    public Producer<VideoInfoConvertEvent> videoInfoConvertEventProducer(PulsarClient client) throws PulsarClientException {
        return client.newProducer(Schema.JSON(VideoInfoConvertEvent.class))
                .topic("video.info.convert.event")
                .accessMode(ProducerAccessMode.Shared)
                .batcherBuilder(BatcherBuilder.DEFAULT)
                .enableBatching(true)
                .compressionType(CompressionType.LZ4)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(0, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();
    }

    @Bean
    public Producer<ControlMessageEvent> atmControlMessageEventProducer(PulsarClient client)
            throws PulsarClientException {
        return client.newProducer(Schema.JSON(ControlMessageEvent.class))
                .topic("atm.control.message.event")
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
