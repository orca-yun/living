package ag.orca.living.api.base;

import ag.orca.living.core.ro.org.OrgExemptWordRo;
import ag.orca.living.core.ro.query.QueryExemptWordRo;
import ag.orca.living.core.vo.org.OrgExemptWordVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrgExemptWordService {


    Pair<List<OrgExemptWordVo>, Long> findPageList(Long orgId, QueryExemptWordRo ro);

    List<OrgExemptWordVo> findList(Long orgId);

    void save(OrgExemptWordRo ro);

    void edit(OrgExemptWordRo ro);

    void remove(Long orgId, List<Long> ids);
}
