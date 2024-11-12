package ag.orca.living.api.interact;

import ag.orca.living.core.ro.interact.BlacklistUserRo;
import ag.orca.living.core.vo.interact.BlacklistUserVo;

import java.util.List;

public interface InteractBlacklistService {
    List<BlacklistUserVo> blacklist(Long roomId);

    void joinUser(Long roomId, BlacklistUserRo ro);

    void removeUser(Long roomId, BlacklistUserRo ro);

    Boolean isInBlacklist(Long roomId, String uid);
}
