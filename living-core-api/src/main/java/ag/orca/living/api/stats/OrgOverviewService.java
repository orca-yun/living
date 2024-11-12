package ag.orca.living.api.stats;

import ag.orca.living.core.vo.stats.LivingOrgOverviewVo;
import ag.orca.living.core.vo.stats.LivingOrgWeekLiveNumVo;

import java.util.List;

public interface OrgOverviewService {

    List<LivingOrgWeekLiveNumVo> weekLiveNumOverview(Long orgId);

    LivingOrgOverviewVo livingOverview(Long orgId);
}
