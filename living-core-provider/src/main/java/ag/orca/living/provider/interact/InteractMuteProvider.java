package ag.orca.living.provider.interact;

import ag.orca.living.api.interact.InteractMuteService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.bo.MuteUserInfo;
import ag.orca.living.core.convert.MuteInfoConvert;
import ag.orca.living.core.entity.room.LivingRoomInteract;
import ag.orca.living.core.enums.OperateAllMuteEnum;
import ag.orca.living.core.repo.examine.ImExamineRepo;
import ag.orca.living.core.repo.interact.MuteRepo;
import ag.orca.living.core.repo.room.LivingRoomInteractRepo;
import ag.orca.living.core.ro.interact.MuteUserRo;
import ag.orca.living.core.vo.interact.MuteUserVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class InteractMuteProvider implements InteractMuteService {

    @Resource
    MuteRepo muteRepo;

    @Resource
    LivingRoomInteractRepo roomInteractRepo;

    @Resource
    ImExamineRepo imExamineRepo;

    @Override
    public List<MuteUserVo> muteList(Long roomId) {
        List<MuteUserInfo> infos = muteRepo.findAllMuteList(roomId);
        return CommonConvert.map(infos, MuteInfoConvert::infoToVo);
    }

    @Override
    public void joinUser(Long roomId, MuteUserRo ro) {
        MuteUserInfo info = MuteInfoConvert.roToInfo(ro);
        if (Objects.nonNull(info)) {
            muteRepo.joinUserToMuteList(roomId, info);
            imExamineRepo.deleteByRoomIdAndUid(roomId, info.getUid());
        }
    }

    @Override
    public void removeUser(Long roomId, MuteUserRo ro) {
        MuteUserInfo info = MuteInfoConvert.roToInfo(ro);
        if (Objects.nonNull(info)) {
            muteRepo.removeUserFromMuteList(roomId, info);
        }
    }

    @Override
    public OperateAllMuteEnum findAllMuteStatus(Long roomId) {
        Optional<LivingRoomInteract> optional = roomInteractRepo.findLivingRoomInteractByRoomId(roomId);
        return OperateAllMuteEnum.ofCode(
                optional.map(LivingRoomInteract::getAllMute)
                        .orElse(OperateAllMuteEnum.off.getCode()));
    }

    @Override
    public void operateAllMute(Long roomId, OperateAllMuteEnum operate) {
        roomInteractRepo.operateAllMuteStatus(roomId, operate);
    }
}
