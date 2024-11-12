package ag.orca.living.api.share;

import ag.orca.living.core.ro.query.QueryShareUserRo;
import ag.orca.living.core.vo.share.LivingShareUserViewRecordVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface LivingShareUserViewRecordService {


    /**
     * 观看用户列表
     *
     * @param ro 房间ID 页码 条数
     * @return
     */
    Pair<List<LivingShareUserViewRecordVo>, Long> findPageList(Long orgId, QueryShareUserRo ro);
}
