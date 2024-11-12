package ag.orca.living.api.base;

import ag.orca.living.core.ro.org.OrgBatchSensitiveWordRo;
import ag.orca.living.core.ro.org.OrgSensitiveWordRo;
import ag.orca.living.core.ro.query.QuerySensitiveWordRo;
import ag.orca.living.core.vo.org.OrgSensitiveWordVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrgSensitiveWordService {

    Pair<List<OrgSensitiveWordVo>, Long> findPageList(Long orgId, QuerySensitiveWordRo ro);

    List<OrgSensitiveWordVo> findList(Long orgId);

    void batchSave(OrgBatchSensitiveWordRo ro);

    void edit(OrgSensitiveWordRo ro);

    void remove(Long orgId, List<Long> ids);
}
