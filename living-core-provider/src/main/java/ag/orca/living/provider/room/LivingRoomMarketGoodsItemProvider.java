package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomMarketGoodsItemService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomMarketGoodsItemConvert;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.repo.room.LivingRoomMarketGoodsItemRepo;
import ag.orca.living.core.ro.room.LivingRoomMarketGoodsItemRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DubboService
@Slf4j
public class LivingRoomMarketGoodsItemProvider implements LivingRoomMarketGoodsItemService {
    @Resource
    LivingRoomMarketGoodsItemRepo livingRoomMarketGoodsItemRepo;


    @Override
    public List<LivingRoomMarketGoodsItemVo> findListByRoomIdForShare(Long roomId) {
        List<LivingRoomMarketGoodsItem> goods = livingRoomMarketGoodsItemRepo.findListByRoomIdForShare(roomId);
        return CommonConvert.map(goods, LivingRoomMarketGoodsItemConvert::entityToVo);
    }

    @Override
    public List<LivingRoomMarketGoodsItemVo> findListByRoomId(Long roomId) {
        List<LivingRoomMarketGoodsItem> goods = livingRoomMarketGoodsItemRepo.findListByRoomId(roomId);
        return CommonConvert.map(goods, LivingRoomMarketGoodsItemConvert::entityToVo);
    }

    @Override
    public Optional<LivingRoomMarketGoodsItemVo> findById(Long id) {
        Optional<LivingRoomMarketGoodsItem> item = livingRoomMarketGoodsItemRepo.findById(id);
        return item.map(LivingRoomMarketGoodsItemConvert::entityToVo);
    }

    @Override
    public void batchSave(Long orgId, Long roomId, LivingRoomMarketGoodsItemRo ros) {
        livingRoomMarketGoodsItemRepo.batchSave(orgId, roomId, ros.getGoodsLibIds());
    }

    @Override
    public List<LivingRoomMarketGoodsItemVo> findListByIdList(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<LivingRoomMarketGoodsItem> goods = livingRoomMarketGoodsItemRepo.findListByIdList(ids);
        return CommonConvert.map(goods, LivingRoomMarketGoodsItemConvert::entityToVo);
    }


}
