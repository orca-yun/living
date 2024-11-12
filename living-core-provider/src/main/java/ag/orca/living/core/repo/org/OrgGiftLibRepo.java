package ag.orca.living.core.repo.org;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.dao.org.OrgGiftLibMapper;
import ag.orca.living.core.dao.room.LivingRoomMarketGiftItemMapper;
import ag.orca.living.core.entity.org.OrgGiftLib;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class OrgGiftLibRepo {

    @Resource
    OrgGiftLibMapper giftLibMapper;

    @Resource
    LivingRoomMarketGiftItemMapper giftItemMapper;

    public List<OrgGiftLib> findListByOrgId(Long orgId) {
        return giftLibMapper.findListByOrgId(orgId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(OrgGiftLib lib) {
        Optional<OrgGiftLib> optional = findSameNameGift(lib.getOrgId(), lib.getName());
        OrcaAssert.match(optional.isPresent(), I18nUtil.getMessage("name.repeat"));
        giftLibMapper.insert(lib);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        int cnt = giftItemMapper.countGiftWithUsed(ids);
        OrcaAssert.match(cnt > 0, I18nUtil.getMessage("gift.lib.item.used"));
        giftLibMapper.logicDel(orgId, ids, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(OrgGiftLib lib) {
        Optional<OrgGiftLib> optional = findSameNameGift(lib.getOrgId(), lib.getName());
        optional.ifPresent(s -> OrcaAssert.match(!Objects.equals(s.getId(), lib.getId()), I18nUtil.getMessage("name.repeat")));
        giftLibMapper.update(lib);
    }

    public Optional<OrgGiftLib> findById(Long id) {
        return Optional.ofNullable(giftLibMapper.findById(id));
    }

    public List<OrgGiftLib> findListByOrgIdAndNameLike(Long orgId, String name) {
        return giftLibMapper.findListByOrgIdAndNameLike(orgId, name);
    }

    public Optional<OrgGiftLib> findSameNameGift(Long orgId, String name) {
        return Optional.ofNullable(giftLibMapper.findSameNameGift(orgId, name));
    }
}
