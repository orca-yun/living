package ag.orca.living.core.repo.room;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomMarketGiftItemConvert;
import ag.orca.living.core.dao.room.LivingRoomMarketGiftItemMapper;
import ag.orca.living.core.entity.org.OrgGiftLib;
import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import ag.orca.living.core.enums.GiftStatusEnum;
import ag.orca.living.core.repo.org.OrgGiftLibRepo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class LivingRoomMarketGiftItemRepo {

    @Resource
    LivingRoomMarketGiftItemMapper livingRoomMarketGiftItemMapper;


    @Resource
    OrgGiftLibRepo orgGiftLibRepo;


    @Transactional(rollbackFor = Exception.class)
    public void updateGiftStatus(Long id, Integer status) {
        livingRoomMarketGiftItemMapper.updateStatusById(id, status, LocalDateTime.now());
    }


    public List<LivingRoomMarketGiftItem> findListByRoomIdForShare(Long roomId) {
        List<LivingRoomMarketGiftItem> items = livingRoomMarketGiftItemMapper.findListByRoomIdAndStatus(roomId, GiftStatusEnum.ON_SHELVES.getCode());
        items = items.stream().sorted(Comparator.comparing(LivingRoomMarketGiftItem::getPriority)).toList();
        return items;
    }

    public List<LivingRoomMarketGiftItem> findListByRoomId(Long roomId) {
        List<LivingRoomMarketGiftItem> items = livingRoomMarketGiftItemMapper.findListByRoomId(roomId);
        items = items.stream().sorted(Comparator.comparing(LivingRoomMarketGiftItem::getPriority)).toList();
        return items;
    }

    public Optional<LivingRoomMarketGiftItem> findById(Long id) {
        return Optional.ofNullable(livingRoomMarketGiftItemMapper.findById(id));
    }

    public List<LivingRoomMarketGiftItem> findByIds(List<Long> ids) {
        return livingRoomMarketGiftItemMapper.findByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(Long orgId, Long roomId, List<Long> giftLibIds) {
        List<LivingRoomMarketGiftItem> dbExists = findListByRoomId(roomId);
        List<Long> dbExistsIds = dbExists.stream().map(LivingRoomMarketGiftItem::getGiftLibId).distinct().toList();
        List<Long> nothing = (List<Long>) CollectionUtils.intersection(dbExistsIds, giftLibIds);
        List<Long> toInsert = (List<Long>) CollectionUtils.subtract(giftLibIds, dbExistsIds);
        List<Long> toDel = (List<Long>) CollectionUtils.subtract(dbExistsIds, giftLibIds);
        if (!CollectionUtils.isEmpty(toDel)) {
            List<Long> ids = CommonConvert.map(
                    CommonConvert.filter(dbExists, s -> toDel.contains(s.getGiftLibId())),
                    LivingRoomMarketGiftItem::getId);
            livingRoomMarketGiftItemMapper.logicDel(null, ids, LocalDateTime.now());
        }
        if (!CollectionUtils.isEmpty(toInsert)) {
            List<OrgGiftLib> gifts = orgGiftLibRepo.findListByOrgId(orgId);
            CommonConvert.map(toInsert, libId -> {
                        Optional<OrgGiftLib> o = gifts.stream().filter(g -> g.getId().equals(libId)).findFirst();
                        return o.map(lib -> LivingRoomMarketGiftItemConvert.libToEntity(roomId, lib)).orElse(null);
                    }).stream()
                    .filter(Objects::nonNull)
                    .forEach(i -> livingRoomMarketGiftItemMapper.insert(i));
        }
        // 重新排序
    }


    @Transactional(rollbackFor = Exception.class)
    public int save(LivingRoomMarketGiftItem livingRoomMarketGoodsItem) {
        return livingRoomMarketGiftItemMapper.insert(livingRoomMarketGoodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(LivingRoomMarketGiftItem livingRoomMarketGoodsItem) {
        return livingRoomMarketGiftItemMapper.update(livingRoomMarketGoodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int remove(Long orgId, List<Long> ids) {
        return livingRoomMarketGiftItemMapper.logicDel(orgId, ids, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void sortedGift(Long roomId, List<Long> itemIds) {
        IntStream.range(1, itemIds.size() + 1).forEach(x -> livingRoomMarketGiftItemMapper.updatePriorityById(itemIds.get(x - 1), x, LocalDateTime.now()));
    }


    @Transactional(rollbackFor = Exception.class)
    public void logicDelByRoomId(Long orgId, Long roomId) {
        livingRoomMarketGiftItemMapper.logicDelByRoomId(roomId, LocalDateTime.now());
    }
}
