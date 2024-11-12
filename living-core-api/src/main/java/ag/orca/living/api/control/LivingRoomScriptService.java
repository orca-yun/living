package ag.orca.living.api.control;

import ag.orca.living.core.ro.control.AddScriptRo;
import ag.orca.living.core.ro.control.UpdateScriptRo;
import ag.orca.living.core.ro.query.QueryScriptRo;
import ag.orca.living.core.vo.control.ControlScriptVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 直播间剧本 service
 *
 * 
 * @date 2024-04-26
 */
public interface LivingRoomScriptService {

    /**
     * 添加直播间剧本
     *
     * @param ro 添加剧本入参
     */
    void addScript(AddScriptRo ro);

    /**
     * 分页查询
     *
     * @param ro 剧本分页查询入参
     * @return
     */
    Pair<List<ControlScriptVo>, Long> findPageList(QueryScriptRo ro);

    /**
     * 批量移除
     *
     * @param ids ID列表
     */
    void remove(List<Long> ids);

    /**
     * 编辑并发送剧本消息
     *
     * @param ro 编辑剧本入参
     */
    void saveScriptAndSend(UpdateScriptRo ro);

    /**
     * 编辑剧本消息
     *
     * @param ro
     */
    void saveScript(UpdateScriptRo ro);

    /**
     * 批量发送剧本
     *
     * @param ids
     */
    void batchToSend(List<Long> ids);


}
