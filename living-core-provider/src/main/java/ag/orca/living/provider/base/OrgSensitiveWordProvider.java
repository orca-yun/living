package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrgSensitiveWordService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.SensitiveWordConvert;
import ag.orca.living.core.entity.org.OrgSensitiveWord;
import ag.orca.living.core.repo.org.OrgSensitiveWordRepo;
import ag.orca.living.core.ro.org.OrgBatchSensitiveWordRo;
import ag.orca.living.core.ro.org.OrgSensitiveWordRo;
import ag.orca.living.core.ro.query.QuerySensitiveWordRo;
import ag.orca.living.core.vo.org.OrgSensitiveWordVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Objects;

@Slf4j
@DubboService
public class OrgSensitiveWordProvider implements OrgSensitiveWordService {

    @Resource
    OrgSensitiveWordRepo sensitiveWordRepo;

    @Override
    public Pair<List<OrgSensitiveWordVo>, Long> findPageList(Long orgId, QuerySensitiveWordRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgSensitiveWord> words = sensitiveWordRepo.findListByOrgIdAndWordsLike(orgId, ro.getWords());
        PageInfo<OrgSensitiveWord> pageInfo = new PageInfo<>(words);
        List<OrgSensitiveWordVo> vos = CommonConvert.map(pageInfo.getList(), SensitiveWordConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<OrgSensitiveWordVo> findList(Long orgId) {
        List<OrgSensitiveWord> words = sensitiveWordRepo.findListByOrgId(orgId);
        return CommonConvert.map(words, SensitiveWordConvert::entityToVo);
    }

    @Override
    public void batchSave(OrgBatchSensitiveWordRo ro) {
        List<OrgSensitiveWord> words = SensitiveWordConvert.batchRoToEntity(ro);
        if (CollectionUtils.isNotEmpty(words)) {
            sensitiveWordRepo.batchSave(ro.getOrgId(), words);
        }
    }

    @Override
    public void edit(OrgSensitiveWordRo ro) {
        if (Objects.nonNull(ro)) {
            sensitiveWordRepo.update(ro.getOrgId(), ro.getId(), ro.getWords());
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            sensitiveWordRepo.remove(orgId, ids);
        }
    }
}
