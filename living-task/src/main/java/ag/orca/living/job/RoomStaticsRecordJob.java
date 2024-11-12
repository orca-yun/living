package ag.orca.living.job;

import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.repo.LiveRoomRepo;
import ag.orca.living.core.repo.LiveRoomStaticsRecordRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@DisallowConcurrentExecution
public class RoomStaticsRecordJob implements SimpleJob {

    @Resource
    LiveRoomStaticsRecordRepo roomStaticsRecordRepo;

    @Resource
    LiveRoomRepo liveRoomRepo;


    @Override
    public void execute(ShardingContext context) {
        String jobName = context.getJobName();
        int sharingItem = context.getShardingItem();
        int total = context.getShardingTotalCount();
        log.info("jobName: {}, {}/{}, started...", jobName, sharingItem, total);
        LocalDateTime trigger = LocalDateTime.now();
        List<LivingRoom> rooms = liveRoomRepo.findAllRooms();

        rooms.forEach(r -> {
            if (r.getId() % total == (long) sharingItem) {
                roomStaticsRecordRepo.processRoomStaticsRecord(trigger, r);
            }
        });
        log.info("jobName: {}, {}/{}, stopped...", jobName, sharingItem, total);
    }

}
