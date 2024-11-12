package ag.orca.living.core.repo.room;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomMarketGoodsItemConvert;
import ag.orca.living.core.dao.room.LivingRoomMarketGoodsItemMapper;
import ag.orca.living.core.entity.org.OrgGoodsLib;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.enums.SellStatusEnum;
import ag.orca.living.core.repo.org.OrgGoodsLibRepo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Repository
public class LivingRoomMarketGoodsItemRepo {

    @Resource
    LivingRoomMarketGoodsItemMapper livingRoomMarketGoodsItemMapper;

    @Resource
    OrgGoodsLibRepo orgGoodsLibRepo;


    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsSellStatus(Long id, Integer sellStatus) {
        livingRoomMarketGoodsItemMapper.updateSellStatusById(id, sellStatus, LocalDateTime.now());
    }


    public List<LivingRoomMarketGoodsItem> findListByRoomId(Long roomId) {
        List<LivingRoomMarketGoodsItem> items = livingRoomMarketGoodsItemMapper.findListByRoomId(roomId);
        items = items.stream().sorted(Comparator.comparing(LivingRoomMarketGoodsItem::getPriority)).toList();
        return items;
    }

    public List<LivingRoomMarketGoodsItem> findListByRoomIdForShare(Long roomId) {
        List<LivingRoomMarketGoodsItem> items = livingRoomMarketGoodsItemMapper.findListByRoomIdAndSellStatus(roomId,
                Arrays.asList(SellStatusEnum.SELL_OUT.getCode(), SellStatusEnum.ON_SHELVES.getCode()));
        items = items.stream().sorted(Comparator.comparing(LivingRoomMarketGoodsItem::getPriority)).toList();
        return items;
    }

    public Optional<LivingRoomMarketGoodsItem> findById(Long id) {
        return Optional.ofNullable(livingRoomMarketGoodsItemMapper.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(Long orgId, Long roomId, List<Long> goodLibIds) {
        List<LivingRoomMarketGoodsItem> dbExists = findListByRoomId(roomId);
        List<Long> dbExistsIds = dbExists.stream().map(LivingRoomMarketGoodsItem::getGoodsLibId).distinct().toList();
        List<Long> nothing = (List<Long>) CollectionUtils.intersection(dbExistsIds, goodLibIds);
        List<Long> toInsert = (List<Long>) CollectionUtils.subtract(goodLibIds, dbExistsIds);
        List<Long> toDel = (List<Long>) CollectionUtils.subtract(dbExistsIds, goodLibIds);
        if (!CollectionUtils.isEmpty(toDel)) {
            List<Long> ids = CommonConvert.map(
                    CommonConvert.filter(dbExists, s -> toDel.contains(s.getGoodsLibId())),
                    LivingRoomMarketGoodsItem::getId);
            livingRoomMarketGoodsItemMapper.logicDel(null, ids, LocalDateTime.now());
        }
        if (!CollectionUtils.isEmpty(toInsert)) {
            List<OrgGoodsLib> goods = orgGoodsLibRepo.findListByOrgId(orgId);
            CommonConvert.map(toInsert, libId -> {
                        Optional<OrgGoodsLib> o = goods.stream().filter(g -> g.getId().equals(libId)).findFirst();
                        return o.map(lib -> LivingRoomMarketGoodsItemConvert.libToEntity(roomId, lib)).orElse(null);
                    }).stream()
                    .filter(Objects::nonNull)
                    .forEach(i -> livingRoomMarketGoodsItemMapper.insert(i));
        }
        // 重新排序
    }


    @Transactional(rollbackFor = Exception.class)
    public int save(LivingRoomMarketGoodsItem livingRoomMarketGoodsItem) {
        return livingRoomMarketGoodsItemMapper.insert(livingRoomMarketGoodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(LivingRoomMarketGoodsItem livingRoomMarketGoodsItem) {
        return livingRoomMarketGoodsItemMapper.update(livingRoomMarketGoodsItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public int remove(Long orgId, List<Long> ids) {
        return livingRoomMarketGoodsItemMapper.logicDel(orgId, ids, LocalDateTime.now());
    }


    @Transactional(rollbackFor = Exception.class)
    public void sortedGoods(Long roomId, List<Long> itemIds) {
        IntStream.range(1, itemIds.size() + 1).forEach(x -> livingRoomMarketGoodsItemMapper.updatePriorityById(itemIds.get(x - 1), x, LocalDateTime.now()));
    }

    public List<LivingRoomMarketGoodsItem> findListByIdList(List<Long> ids) {
        return livingRoomMarketGoodsItemMapper.findListByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    public void logicDelByRoomId(Long orgId, Long roomId) {
        livingRoomMarketGoodsItemMapper.logicDelByRoomId(roomId, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateSellStatus(List<Long> modIds, SellStatusEnum status) {
        livingRoomMarketGoodsItemMapper.batchUpdateSellStatus(modIds, status.getCode(), LocalDateTime.now());
    }
}
