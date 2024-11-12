package ag.orca.living.provider.base;

import ag.orca.living.api.base.OrgGoodsLibService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.OrgGoodsLibConvert;
import ag.orca.living.core.entity.org.OrgGoodsLib;
import ag.orca.living.core.repo.org.OrgGoodsLibRepo;
import ag.orca.living.core.ro.org.OrgGoodsLibRo;
import ag.orca.living.core.ro.query.QueryGoodsRo;
import ag.orca.living.core.vo.org.OrgGoodsLibVo;
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
public class OrgGoodsLibProvider implements OrgGoodsLibService {

    @Resource
    OrgGoodsLibRepo goodsLibRepo;

    @Override
    public Pair<List<OrgGoodsLibVo>, Long> findPageList(Long orgId, QueryGoodsRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgGoodsLib> goodsLibs = goodsLibRepo.findListByOrgIdAndNameLike(orgId, ro.getName());
        PageInfo<OrgGoodsLib> pageInfo = new PageInfo<>(goodsLibs);
        List<OrgGoodsLibVo> vos = CommonConvert.map(pageInfo.getList(), OrgGoodsLibConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public List<OrgGoodsLibVo> findList(Long orgId) {
        List<OrgGoodsLib> libs = goodsLibRepo.findListByOrgId(orgId);
        return CommonConvert.map(libs, OrgGoodsLibConvert::entityToVo);
    }

    @Override
    public void save(OrgGoodsLibRo ro) {
        OrgGoodsLib lib = OrgGoodsLibConvert.roToEntity(ro);
        if (Objects.nonNull(lib)) {
            goodsLibRepo.save(lib);
        }
    }

    @Override
    public void remove(Long orgId, List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            goodsLibRepo.remove(orgId, ids);
        }
    }

    @Override
    public void edit(OrgGoodsLibRo ro) {
        OrgGoodsLib lib = OrgGoodsLibConvert.roToEntity(ro);
        if (Objects.nonNull(lib)) {
            goodsLibRepo.update(lib);
        }
    }
}
