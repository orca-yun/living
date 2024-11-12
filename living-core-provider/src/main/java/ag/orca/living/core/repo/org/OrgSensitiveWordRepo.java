package ag.orca.living.core.repo.org;

import ag.orca.living.common.CacheConst;
import ag.orca.living.core.convert.SensitiveWordConvert;
import ag.orca.living.core.dao.org.OrgSensitiveWordMapper;
import ag.orca.living.core.entity.org.OrgSensitiveGroup;
import ag.orca.living.core.entity.org.OrgSensitiveWord;
import ag.orca.living.core.repo.room.AbstractObjCacheRepo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrgSensitiveWordRepo extends AbstractObjCacheRepo<OrgSensitiveGroup> {

    @Resource
    OrgSensitiveWordMapper sensitiveWordMapper;

    @Override
    protected String getPrefix() {
        return CacheConst.SENSITIVE_PREFIX;
    }

    @Override
    protected OrgSensitiveGroup getFromDb(Long id) {
        List<OrgSensitiveWord> words = sensitiveWordMapper.findListByOrgId(id);
        return SensitiveWordConvert.buildGroup(id, words);
    }

    @Override
    protected Class<OrgSensitiveGroup> clazz() {
        return OrgSensitiveGroup.class;
    }

    public List<OrgSensitiveWord> findListByOrgId(Long orgId) {
        return sensitiveWordMapper.findListByOrgId(orgId);
    }

    public List<OrgSensitiveWord> findListByOrgIdAndWordsLike(Long orgId, String words) {
        return sensitiveWordMapper.findListByOrgIdAndWordsLike(orgId, words);
    }


    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        sensitiveWordMapper.logicDel(orgId, ids, LocalDateTime.now());
        refreshCache(orgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Long orgId, Long id, String words) {
        sensitiveWordMapper.updateWordsById(id, words);
        refreshCache(orgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(Long orgId, List<OrgSensitiveWord> words) {
        sensitiveWordMapper.batchInsert(words);
        refreshCache(orgId);
    }


}
