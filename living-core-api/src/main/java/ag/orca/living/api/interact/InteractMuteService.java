package ag.orca.living.api.interact;

import ag.orca.living.core.enums.OperateAllMuteEnum;
import ag.orca.living.core.ro.interact.MuteUserRo;
import ag.orca.living.core.vo.interact.MuteUserVo;

import java.util.List;

public interface InteractMuteService {
    List<MuteUserVo> muteList(Long roomId);

    void joinUser(Long roomId, MuteUserRo ro);

    void removeUser(Long roomId, MuteUserRo ro);

    OperateAllMuteEnum findAllMuteStatus(Long roomId);

    void operateAllMute(Long roomId, OperateAllMuteEnum operate);
}
