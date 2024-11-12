package ag.orca.living.api.interact;

import ag.orca.living.core.ro.PageRo;
import ag.orca.living.core.vo.interact.ShareOnlineVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface InteractShareOnlineService {
    Pair<List<ShareOnlineVo>, Long> findPageShareOnlineList(Long roomId, PageRo ro);

    Long totalShareOnline(Long roomId);
}
