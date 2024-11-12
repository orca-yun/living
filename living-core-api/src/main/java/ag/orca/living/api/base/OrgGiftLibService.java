package ag.orca.living.api.base;

import ag.orca.living.core.ro.org.OrgGiftLibRo;
import ag.orca.living.core.ro.query.QueryGiftRo;
import ag.orca.living.core.vo.org.OrgGiftLibVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrgGiftLibService {
    /**
     * 礼品库列表
     *
     * @param orgId
     * @param ro
     * @return
     */
    Pair<List<OrgGiftLibVo>, Long> findPageList(Long orgId, QueryGiftRo ro);

    /**
     * 礼品库列表
     *
     * @param orgId
     * @return
     */
    List<OrgGiftLibVo> findList(Long orgId);

    /**
     * 保存
     *
     * @param ro
     */
    void save(OrgGiftLibRo ro);


    /**
     * 删除
     *
     * @param orgId 机构ID
     * @param ids   ID列表
     */
    void remove(Long orgId, List<Long> ids);

    /***
     * 编辑
     * @param ro
     */
    void edit(OrgGiftLibRo ro);
}
