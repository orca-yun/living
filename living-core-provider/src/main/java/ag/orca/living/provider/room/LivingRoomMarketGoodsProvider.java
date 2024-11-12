package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomMarketGoodsService;
import ag.orca.living.core.convert.LivingRoomMarketGoodsConvert;
import ag.orca.living.core.entity.room.LivingRoomMarketGoods;
import ag.orca.living.core.repo.room.LivingRoomMarketGoodsRepo;
import ag.orca.living.core.ro.room.LivingRoomMarketGoodsRo;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;
import java.util.Optional;


@DubboService
@Slf4j
public class LivingRoomMarketGoodsProvider implements LivingRoomMarketGoodsService {
    @Resource
    LivingRoomMarketGoodsRepo livingRoomMarketGoodsRepo;

    @Override
    public Optional<LivingRoomMarketGoodsVo> findByRoomId(Long roomId) {
        return livingRoomMarketGoodsRepo.findLivingRoomMarketGoodsByRoomId(roomId).map(LivingRoomMarketGoodsConvert::entityToVo);
    }

    @Override
    public void save(LivingRoomMarketGoodsRo ro) {
        LivingRoomMarketGoods entity = LivingRoomMarketGoodsConvert.roToEntity(ro);
        if (Objects.nonNull(entity)) {
            livingRoomMarketGoodsRepo.save(entity);
        }
    }

}
