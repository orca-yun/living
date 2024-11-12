package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomInteractService;
import ag.orca.living.core.convert.LivingRoomInteractConvert;
import ag.orca.living.core.entity.room.LivingRoomInteract;
import ag.orca.living.core.repo.room.LivingRoomInteractRepo;
import ag.orca.living.core.ro.room.LivingRoomInteractRo;
import ag.orca.living.core.vo.room.LivingRoomInteractVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@DubboService
public class LivingRoomInteractProvider implements LivingRoomInteractService {

    @Resource
    LivingRoomInteractRepo livingRoomInteractRepo;


    @Override
    public Optional<LivingRoomInteractVo> findByRoomId(Long roomId) {
        return livingRoomInteractRepo.findLivingRoomInteractByRoomId(roomId).map(LivingRoomInteractConvert::entityToVo);
    }

    @Override
    public void save(LivingRoomInteractRo ro) {
        LivingRoomInteract entity = LivingRoomInteractConvert.roToEntity(ro);
        if (Objects.nonNull(entity)) {
            livingRoomInteractRepo.save(entity);
        }
    }

}
