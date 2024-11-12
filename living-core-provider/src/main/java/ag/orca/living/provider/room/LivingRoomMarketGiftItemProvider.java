package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomMarketGiftItemService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingRoomMarketGiftItemConvert;
import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import ag.orca.living.core.repo.room.LivingRoomMarketGiftItemRepo;
import ag.orca.living.core.ro.room.LivingRoomMarketGiftItemRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Optional;

@DubboService
@Slf4j
public class LivingRoomMarketGiftItemProvider implements LivingRoomMarketGiftItemService {
    @Resource
    LivingRoomMarketGiftItemRepo livingRoomMarketGiftItemRepo;


    @Override
    public List<LivingRoomMarketGiftItemVo> findListByRoomIdForShare(Long roomId) {
        List<LivingRoomMarketGiftItem> items = livingRoomMarketGiftItemRepo.findListByRoomIdForShare(roomId);
        return CommonConvert.map(items, LivingRoomMarketGiftItemConvert::entityToVo);
    }

    @Override
    public List<LivingRoomMarketGiftItemVo> findListByRoomId(Long roomId) {
        List<LivingRoomMarketGiftItem> items = livingRoomMarketGiftItemRepo.findListByRoomId(roomId);
        return CommonConvert.map(items, LivingRoomMarketGiftItemConvert::entityToVo);
    }

    @Override
    public Optional<LivingRoomMarketGiftItemVo> findById(Long id) {
        Optional<LivingRoomMarketGiftItem> item = livingRoomMarketGiftItemRepo.findById(id);
        return item.map(LivingRoomMarketGiftItemConvert::entityToVo);
    }

    @Override
    public void batchSave(Long orgId, Long roomId, LivingRoomMarketGiftItemRo ros) {
        livingRoomMarketGiftItemRepo.batchSave(orgId, roomId, ros.getGiftLibIds());
    }


}
