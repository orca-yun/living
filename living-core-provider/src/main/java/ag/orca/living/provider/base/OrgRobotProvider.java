package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrgRobotService;
import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.OrgRobotConvert;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.repo.org.OrgRobotRepo;
import ag.orca.living.core.ro.org.OrgBatchRobotRo;
import ag.orca.living.core.ro.org.OrgRobotRo;
import ag.orca.living.core.ro.query.QueryRobotRo;
import ag.orca.living.core.vo.org.OrgRobotVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Objects;

@Slf4j
@DubboService
public class OrgRobotProvider implements OrgRobotService {

    @Resource
    OrgRobotRepo robotRepo;

    @DubboReference
    LivingSeqService seqService;

    @Override
    public Pair<List<OrgRobotVo>, Long> findPageList(Long orgId, QueryRobotRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgRobot> robots = robotRepo.findListByOrgIdAndNicknameLike(orgId, ro.getNickname());
        PageInfo<OrgRobot> pageInfo = new PageInfo<>(robots);
        List<OrgRobotVo> vos = CommonConvert.map(pageInfo.getList(), OrgRobotConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<OrgRobotVo> findList(Long orgId) {
        List<OrgRobot> robots = robotRepo.findListByOrgId(orgId);
        return CommonConvert.map(robots, OrgRobotConvert::entityToVo);
    }


    @Override
    public void batchSave(OrgBatchRobotRo ro) {
        List<OrgRobot> robots = OrgRobotConvert.batchRoToEntity(ro);
        if (CollectionUtils.isNotEmpty(robots)) {
            robots.forEach(s -> s.setCode(seqService.uuid()));
            robotRepo.batchSave(robots);
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            robotRepo.remove(orgId, ids);
        }
    }

    @Override
    public void edit(OrgRobotRo ro) {
        OrgRobot robot = OrgRobotConvert.roToEntity(ro);
        if (Objects.nonNull(robot)) {
            robotRepo.update(robot);
        }

    }
}
