package ag.orca.living.pulsar.listener;

import ag.orca.living.core.enums.PushTaskStatusEnum;
import ag.orca.living.core.event.VideoPushTaskStatusEvent;
import ag.orca.living.core.repo.live.LivingTriggerOplogRepo;
import ag.orca.living.core.repo.live.LivingVideoRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@Component
public class VideoPushTaskStatusEventMessageListener implements MessageListener<VideoPushTaskStatusEvent> {


    @Resource
    LivingTriggerOplogRepo triggerOplogRepo;

    @Resource
    LivingVideoRepo livingVideoRepo;


    /**
     * 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败
     * <p>
     * Stream [0 的时间比较短]
     * 0 等待启动
     * 1 运行中
     * 2 已停止,正常结束(自动｜主动)
     * 3 已停止且失败
     */

    @SneakyThrows
    @Override
    public void received(Consumer<VideoPushTaskStatusEvent> consumer, Message<VideoPushTaskStatusEvent> msg) {
        VideoPushTaskStatusEvent event = msg.getValue();
        if (Objects.isNull(event.getStatus())) {
            //更新进度 推流任务ID 在我们系统里面有没有
            triggerOplogRepo.updateDurationByPushTaskId(event.getDuration(), event.getPushTaskId());
        } else {
            PushTaskStatusEnum status = PushTaskStatusEnum.ofCode(event.getStatus());
            if (status == PushTaskStatusEnum.running) {
                // 推流任务ID 在我们系统里面有没有
                // 如果是开始直播 自动开启直播记录
                long oplogId = Long.parseLong(event.getOplogId());
                triggerOplogRepo.renderPushTaskInfoById(event, oplogId);
                livingVideoRepo.autoStartLive(event);
            } else if (status == PushTaskStatusEnum.failed || status == PushTaskStatusEnum.stop) {
                //  推流任务ID 在我们系统里面有没有
                // 如果是直播结束 自动结束直播  推流任务ID 在我们系统里面有没有
                livingVideoRepo.autoStopLive(event);
            }
        }
        consumer.acknowledge(msg);
    }
}
