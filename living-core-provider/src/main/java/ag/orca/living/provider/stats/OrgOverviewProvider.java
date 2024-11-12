package ag.orca.living.provider.stats;

import ag.orca.living.api.stats.OrgOverviewService;
import ag.orca.living.core.repo.live.LivingRecordRepo;
import ag.orca.living.core.repo.media.LivingMediaLibRepo;
import ag.orca.living.core.repo.overview.LivingRoomStaticsRepo;
import ag.orca.living.core.vo.stats.LivingOrgOverviewVo;
import ag.orca.living.core.vo.stats.LivingOrgWeekLiveNumVo;
import ag.orca.living.util.DateUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@DubboService
public class OrgOverviewProvider implements OrgOverviewService {

    @Resource
    LivingRecordRepo livingRecordRepo;

    @Resource
    LivingMediaLibRepo livingMediaLibRepo;

    @Resource
    LivingRoomStaticsRepo livingRoomStaticsRepo;

    @Override
    public List<LivingOrgWeekLiveNumVo> weekLiveNumOverview(Long orgId) {
        LocalDate stop = LocalDate.now();
        LocalDate start = stop.minusWeeks(1);
        List<LivingOrgWeekLiveNumVo> numVos = livingRecordRepo.weekLiveNum(orgId, start, stop);
        return DateUtil.dateRangeFunc(start, stop, Pair.of(ChronoUnit.DAYS, 1L), ld -> {
            LivingOrgWeekLiveNumVo v = LivingOrgWeekLiveNumVo.builder().date(ld).build();
            if (CollectionUtils.isNotEmpty(numVos)) {
                return numVos.stream().filter(s -> s.getDate().equals(ld))
                        .findFirst().orElse(v);
            }
            return v;
        });
    }

    @Override
    public LivingOrgOverviewVo livingOverview(Long orgId) {
        LivingOrgOverviewVo vo = new LivingOrgOverviewVo();
        Optional<Long> liveNum = livingRecordRepo.totalLiveNum(orgId);
        Optional<Long> storage = livingMediaLibRepo.totalStorage(orgId);
        Optional<Long> liveUv = livingRoomStaticsRepo.totalLiveUv(orgId);
        liveNum.ifPresent(vo::setLiveNum);
        storage.ifPresent(vo::setStorage);
        liveUv.ifPresent(vo::setLiveUv);
        // playUv todo
        return vo;
    }
}
