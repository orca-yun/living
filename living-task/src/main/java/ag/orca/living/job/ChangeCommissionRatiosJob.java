package ag.orca.living.job;

import ag.orca.living.core.dao.channel.ChannelInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@DisallowConcurrentExecution
public class ChangeCommissionRatiosJob implements SimpleJob {

    @Resource
    ChannelInfoMapper channelInfoMapper;

    @Override
    public void execute(ShardingContext context) {
        String jobName = context.getJobName();
        log.info("jobName: {} ", jobName);
        channelInfoMapper.updateCommissionRatio(LocalDateTime.now());
        log.info("渠道分佣比例定时更改成功 {}", LocalDateTime.now());
    }
}
