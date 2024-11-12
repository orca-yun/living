package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomPageService;
import ag.orca.living.core.convert.LivingRoomPageConvert;
import ag.orca.living.core.entity.room.LivingRoomPage;
import ag.orca.living.core.repo.room.LivingRoomPageRepo;
import ag.orca.living.core.ro.room.LivingRoomPageRo;
import ag.orca.living.core.vo.room.LivingRoomPageVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class LivingRoomPageProvider implements LivingRoomPageService {

    @Resource
    LivingRoomPageRepo livingRoomPageRepo;

    @Override
    public Optional<LivingRoomPageVo> findByRoomId(Long roomId) {
        return livingRoomPageRepo.findLivingRoomPageByRoomId(roomId).map(LivingRoomPageConvert::entityToVo);
    }

    @Override
    public void save(LivingRoomPageRo ro) {
        LivingRoomPage entity = LivingRoomPageConvert.roToEntity(ro);
        if (Objects.nonNull(entity)) {
            livingRoomPageRepo.save(entity);
        }
    }

}