package ag.orca.living.api.base;

import ag.orca.living.core.ro.org.OrgGoodsLibRo;
import ag.orca.living.core.ro.query.QueryGoodsRo;
import ag.orca.living.core.vo.org.OrgGoodsLibVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrgGoodsLibService {

    /**
     * 商品库列表
     *
     * @param orgId
     * @param ro
     * @return
     */
    Pair<List<OrgGoodsLibVo>, Long> findPageList(Long orgId, QueryGoodsRo ro);

    /**
     * 商品库列表
     *
     * @param orgId 机构ID
     * @return
     */
    List<OrgGoodsLibVo> findList(Long orgId);

    /**
     * 保存商品库
     *
     * @param ro
     */
    void save(OrgGoodsLibRo ro);


    /**
     * 删除
     *
     * @param orgId 机构ID
     * @param ids   删除列表
     */
    void remove(Long orgId, List<Long> ids);

    /**
     * 编辑
     *
     * @param ro
     */
    void edit(OrgGoodsLibRo ro);
}
