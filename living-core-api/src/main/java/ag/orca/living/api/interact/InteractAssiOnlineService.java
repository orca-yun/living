package ag.orca.living.api.interact;

import ag.orca.living.core.vo.interact.AssiOnlineVo;

import java.util.List;

public interface InteractAssiOnlineService {
    List<AssiOnlineVo> findAssiOnlineList(Long roomId);
}
