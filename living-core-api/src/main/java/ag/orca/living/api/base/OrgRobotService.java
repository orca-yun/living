package ag.orca.living.api.base;

import ag.orca.living.core.ro.org.OrgBatchRobotRo;
import ag.orca.living.core.ro.org.OrgRobotRo;
import ag.orca.living.core.ro.query.QueryRobotRo;
import ag.orca.living.core.vo.org.OrgRobotVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrgRobotService {

    /**
     * 机器人列表
     *
     * @param ro 机构ID 页码 条数
     * @return
     */
    Pair<List<OrgRobotVo>, Long> findPageList(Long orgId, QueryRobotRo ro);


    /**
     * 机器人列表
     *
     * @param orgId 机构ID
     * @return
     */
    List<OrgRobotVo> findList(Long orgId);

    /**
     * 批量保存机器人
     *
     * @param ro
     */
    void batchSave(OrgBatchRobotRo ro);


    /**
     * 删除机器人
     *
     * @param orgId 机构ID
     * @param ids   ID列表
     */
    void remove(Long orgId, List<Long> ids);

    /**
     * 编辑机器人
     *
     * @param ro
     */
    void edit(OrgRobotRo ro);
}
