package ag.orca.living.core.repo.org;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ExemptWordConvert;
import ag.orca.living.core.dao.org.OrgExemptWordMapper;
import ag.orca.living.core.dao.room.LivingRoomInteractMapper;
import ag.orca.living.core.entity.org.OrgExemptGroup;
import ag.orca.living.core.entity.org.OrgExemptWord;
import ag.orca.living.core.repo.room.AbstractObjCacheRepo;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static ag.orca.living.common.CacheConst.EXEMPT_PREFIX;

@Repository
public class OrgExemptWordRepo extends AbstractObjCacheRepo<OrgExemptGroup> {

    @Resource
    OrgExemptWordMapper exemptWordMapper;

    @Resource
    LivingRoomInteractMapper roomInteractMapper;


    @Override
    protected String getPrefix() {
        return EXEMPT_PREFIX;
    }

    @Override
    protected OrgExemptGroup getFromDb(Long id) {
        OrgExemptWord word = exemptWordMapper.findById(id);
        return ExemptWordConvert.buildGroup(word);
    }

    @Override
    protected Class<OrgExemptGroup> clazz() {
        return OrgExemptGroup.class;
    }


    public List<OrgExemptWord> findListByOrgId(Long orgId) {
        return exemptWordMapper.findListByOrgId(orgId);
    }

    public List<OrgExemptWord> findListByOrgIdAndNameLike(Long orgId, String name) {
        return exemptWordMapper.findListByOrgIdAndNameLike(orgId, name);
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(OrgExemptWord word) {
        exemptWordMapper.insert(word);
        refreshCache(word.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        int count = roomInteractMapper.countExemptWithUsed(ids);
        OrcaAssert.match(count > 0, I18nUtil.getMessage("exempt.word.used"));
        exemptWordMapper.logicDel(orgId, ids, LocalDateTime.now());
        ids.forEach(this::refreshCache);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, String name, String words) {
        exemptWordMapper.updateWordsById(id, name, words);
        refreshCache(id);
    }


}
