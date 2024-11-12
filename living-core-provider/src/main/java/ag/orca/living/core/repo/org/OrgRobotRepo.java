package ag.orca.living.core.repo.org;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.dao.org.OrgRobotMapper;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.util.I18nUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrgRobotRepo {

    @Resource
    OrgRobotMapper robotMapper;

    public List<OrgRobot> findListByOrgId(Long orgId) {
        return robotMapper.findListByOrgId(orgId);
    }

    public List<OrgRobot> findListByOrgIdAndNicknameLike(Long orgId, String nickname) {
        return robotMapper.findListByOrgIdAndNicknameLike(orgId, nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<OrgRobot> robots) {
        robotMapper.batchInsert(robots);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        robotMapper.logicDel(orgId, ids, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(OrgRobot robot) {
        Optional<OrgRobot> optional = findSameNickNameRobot(robot.getOrgId(), robot.getNickname());
        optional.ifPresent(s -> OrcaAssert.match(!Objects.equals(s.getId(), robot.getId()), I18nUtil.getMessage("name.repeat")));
        robotMapper.update(robot);
    }

    public Optional<OrgRobot> findSameNickNameRobot(Long orgId, String nickname) {
        return Optional.ofNullable(robotMapper.findSameNickNameRobot(orgId, nickname));
    }

    public Optional<OrgRobot> findById(Long id) {
        return Optional.ofNullable(robotMapper.findById(id));
    }

    public List<OrgRobot> findByIds(List<Long> ids) {
        return robotMapper.findByIds(ids);
    }

    public Pair<List<OrgRobot>, Long> findPageListByOrgId(Integer page, Integer pageSize, Long orgId) {
        PageHelper.startPage(page, pageSize);
        List<OrgRobot> robots = robotMapper.findListByOrgId(orgId);
        PageInfo<OrgRobot> pageInfo = PageInfo.of(robots);
        return Pair.of(pageInfo.getList(), pageInfo.getTotal());
    }

    public int countByOrgId(Long orgId) {
        return robotMapper.countByOrgId(orgId);
    }


}
