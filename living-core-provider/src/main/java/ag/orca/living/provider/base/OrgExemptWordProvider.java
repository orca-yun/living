package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrgExemptWordService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.ExemptWordConvert;
import ag.orca.living.core.entity.org.OrgExemptWord;
import ag.orca.living.core.repo.org.OrgExemptWordRepo;
import ag.orca.living.core.ro.org.OrgExemptWordRo;
import ag.orca.living.core.ro.query.QueryExemptWordRo;
import ag.orca.living.core.vo.org.OrgExemptWordVo;
import ag.orca.living.util.JsonUtil;
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
public class OrgExemptWordProvider implements OrgExemptWordService {

    @Resource
    OrgExemptWordRepo exemptWordRepo;


    @Override
    public Pair<List<OrgExemptWordVo>, Long> findPageList(Long orgId, QueryExemptWordRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgExemptWord> words = exemptWordRepo.findListByOrgIdAndNameLike(orgId, ro.getName());
        PageInfo<OrgExemptWord> pageInfo = new PageInfo<>(words);
        List<OrgExemptWordVo> vos = CommonConvert.map(pageInfo.getList(), ExemptWordConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<OrgExemptWordVo> findList(Long orgId) {
        List<OrgExemptWord> words = exemptWordRepo.findListByOrgId(orgId);
        return CommonConvert.map(words, ExemptWordConvert::entityToVo);
    }

    @Override
    public void save(OrgExemptWordRo ro) {
        OrgExemptWord word = ExemptWordConvert.roToEntity(ro);
        if (Objects.nonNull(word)) {
            exemptWordRepo.save(word);
        }
    }

    @Override
    public void edit(OrgExemptWordRo ro) {
        if (Objects.nonNull(ro)) {
            exemptWordRepo.update(ro.getId(), ro.getName(), JsonUtil.beanToJson(ro.getWords()));
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            exemptWordRepo.remove(orgId, ids);
        }
    }
}
