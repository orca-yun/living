package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomPermissionService;
import ag.orca.living.core.convert.LivingRoomPermissionConvert;
import ag.orca.living.core.entity.room.LivingRoomPermission;
import ag.orca.living.core.repo.room.LivingRoomPermissionRepo;
import ag.orca.living.core.ro.room.LivingRoomPermissionRo;
import ag.orca.living.core.vo.room.LivingRoomPermissionVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class LivingRoomPermissionProvider implements LivingRoomPermissionService {

    @Resource
    LivingRoomPermissionRepo livingRoomPermissionRepo;

    @Override
    public Optional<LivingRoomPermissionVo> findByRoomId(Long roomId) {
        return livingRoomPermissionRepo.findLivingRoomPermissionByRoomId(roomId)
                .map(LivingRoomPermissionConvert::entityToVo);
    }

    @Override
    public void save(LivingRoomPermissionRo ro) {
        LivingRoomPermission entity = LivingRoomPermissionConvert.roToEntity(ro);
        if (Objects.nonNull(entity)) {
            livingRoomPermissionRepo.save(entity);
        }
    }


}
