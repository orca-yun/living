package ag.orca.living.api.base;

import ag.orca.living.core.ro.query.QueryAccountRecordRo;
import ag.orca.living.core.vo.org.OrgAccountRecordVo;
import ag.orca.living.core.vo.org.OrgAccountVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface OrgAccountService {


    Optional<OrgAccountVo> findOrgAccountByOrgId(Long orgId);

    /**
     * 企业账户流水记录
     *
     * @param orgId 机构ID
     * @param ro    客服RO
     * @return
     */
    Pair<List<OrgAccountRecordVo>, Long> findAccountRecordPageList(Long orgId, QueryAccountRecordRo ro);


}
