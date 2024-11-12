package ag.orca.living.pulsar.listener;

import ag.orca.living.core.event.CoursewareConvertEvent;
import ag.orca.living.core.repo.stream.LivingCoursewareRepo;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoursewareConvertEventMessageListener implements MessageListener<CoursewareConvertEvent> {

    @Resource
    LivingCoursewareRepo livingCoursewareRepo;


    @SneakyThrows
    @Override
    public void received(Consumer<CoursewareConvertEvent> consumer, Message<CoursewareConvertEvent> msg) {
        CoursewareConvertEvent event = msg.getValue();
        log.info("课件转码处理 => {}", event);
        livingCoursewareRepo.startConvertPdfToImageRecord(event);
        consumer.acknowledge(msg);
    }
}
