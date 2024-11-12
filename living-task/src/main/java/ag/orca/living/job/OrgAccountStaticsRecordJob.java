package ag.orca.living.job;

import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.repo.OrgAccountStaticsRepo;
import ag.orca.living.core.repo.OrgRepo;
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
public class OrgAccountStaticsRecordJob implements SimpleJob {

    @Resource
    OrgRepo orgRepo;

    @Resource
    OrgAccountStaticsRepo orgAccountStaticsRepo;


    @Override
    public void execute(ShardingContext context) {
        String jobName = context.getJobName();
        int sharingItem = context.getShardingItem();
        int total = context.getShardingTotalCount();
        log.info("jobName: {}, {}/{}, started...", jobName, sharingItem, total);
        LocalDateTime trigger = LocalDateTime.now();
        List<Organization> organizations = orgRepo.findAllOrg();
        organizations.forEach(r -> {
            if (r.getId() % total == (long) sharingItem) {
                orgAccountStaticsRepo.processOrgAccount(trigger, r);
            }
        });

    }
}
