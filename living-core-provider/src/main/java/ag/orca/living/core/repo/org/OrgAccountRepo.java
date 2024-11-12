package ag.orca.living.core.repo.org;

import ag.orca.living.core.dao.org.OrgAccountMapper;
import ag.orca.living.core.dao.org.OrgAccountRecordMapper;
import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.entity.org.OrgAccountRecord;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrgAccountRepo {


    @Resource
    OrgAccountMapper orgAccountMapper;

    @Resource
    OrgAccountRecordMapper orgAccountRecordMapper;


    public List<OrgAccountRecord> findAccountRecordListByOrgIdAndRecordType(Long orgId, Integer recordType) {
        return orgAccountRecordMapper.findListByOrgIdAndRecordType(orgId, recordType);
    }

    public Optional<OrgAccount> findOrgAccountByOrgId(Long orgId) {
        OrgAccount account = orgAccountMapper.findFirstByOrgId(orgId);
        return Optional.ofNullable(account);
    }
}
