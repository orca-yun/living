package ag.orca.living.core.repo.org;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.dao.org.OrgGoodsLibMapper;
import ag.orca.living.core.dao.room.LivingRoomMarketGoodsItemMapper;
import ag.orca.living.core.entity.org.OrgGoodsLib;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrgGoodsLibRepo {

    @Resource
    OrgGoodsLibMapper goodsLibMapper;

    @Resource
    LivingRoomMarketGoodsItemMapper goodsItemMapper;

    public List<OrgGoodsLib> findListByOrgId(Long orgId) {
        return goodsLibMapper.findListByOrgId(orgId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(OrgGoodsLib lib) {
        Optional<OrgGoodsLib> optional = findSameNameGoods(lib.getOrgId(), lib.getName());
        OrcaAssert.match(optional.isPresent(), I18nUtil.getMessage("name.repeat"));
        goodsLibMapper.insert(lib);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        int cnt = goodsItemMapper.countGoodsWithUsed(ids);
        OrcaAssert.match(cnt > 0, I18nUtil.getMessage("goods.lib.item.used"));
        goodsLibMapper.logicDel(orgId, ids, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(OrgGoodsLib lib) {
        Optional<OrgGoodsLib> optional = findSameNameGoods(lib.getOrgId(), lib.getName());
        optional.ifPresent(s -> OrcaAssert.match(!Objects.equals(s.getId(), lib.getId()), I18nUtil.getMessage("name.repeat")));
        goodsLibMapper.update(lib);
    }

    public List<OrgGoodsLib> findListByOrgIdAndNameLike(Long orgId, String name) {
        return goodsLibMapper.findListByOrgIdAndNameLike(orgId, name);
    }

    public List<OrgGoodsLib> findListByIds(List<Long> ids) {
        return goodsLibMapper.findListByIds(ids);
    }


    public Optional<OrgGoodsLib> findSameNameGoods(Long orgId, String name) {
        return Optional.ofNullable(goodsLibMapper.findSameNameGoods(orgId, name));
    }
}
