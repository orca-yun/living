package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrgGiftLibService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.OrgGiftLibConvert;
import ag.orca.living.core.entity.org.OrgGiftLib;
import ag.orca.living.core.repo.org.OrgGiftLibRepo;
import ag.orca.living.core.ro.org.OrgGiftLibRo;
import ag.orca.living.core.ro.query.QueryGiftRo;
import ag.orca.living.core.vo.org.OrgGiftLibVo;
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
public class OrgGiftLibProvider implements OrgGiftLibService {

    @Resource
    OrgGiftLibRepo giftLibRepo;

    @Override
    public Pair<List<OrgGiftLibVo>, Long> findPageList(Long orgId, QueryGiftRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgGiftLib> giftLibs = giftLibRepo.findListByOrgIdAndNameLike(orgId, ro.getName());
        PageInfo<OrgGiftLib> pageInfo = new PageInfo<>(giftLibs);
        List<OrgGiftLibVo> vos = CommonConvert.map(pageInfo.getList(), OrgGiftLibConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<OrgGiftLibVo> findList(Long orgId) {
        List<OrgGiftLib> libs = giftLibRepo.findListByOrgId(orgId);
        return CommonConvert.map(libs, OrgGiftLibConvert::entityToVo);
    }

    @Override
    public void save(OrgGiftLibRo ro) {
        OrgGiftLib lib = OrgGiftLibConvert.roToEntity(ro);
        if (Objects.nonNull(lib)) {
            giftLibRepo.save(lib);
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            giftLibRepo.remove(orgId, ids);
        }
    }

    @Override
    public void edit(OrgGiftLibRo ro) {
        OrgGiftLib lib = OrgGiftLibConvert.roToEntity(ro);
        if (Objects.nonNull(lib)) {
            giftLibRepo.update(lib);
        }

    }
}
