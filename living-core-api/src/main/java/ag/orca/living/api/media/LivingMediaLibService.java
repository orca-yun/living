package ag.orca.living.api.media;

import ag.orca.living.core.ro.media.LivingMediaLibEditRo;
import ag.orca.living.core.ro.query.QueryMediaLibRo;
import ag.orca.living.core.vo.media.LivingMediaLibInfoVo;
import ag.orca.living.core.vo.media.LivingMediaLibVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface LivingMediaLibService {
    Pair<List<LivingMediaLibInfoVo>, Long> findPageList(Long orgId, QueryMediaLibRo ro);

    void remove(Long orgId, List<Long> ids);

    void saveByManual(Long orgId, String name, String fileName, String bucket, String key);

    Optional<LivingMediaLibVo> findFirstById(Long id);

    void editMediaLibName(Long orgId, LivingMediaLibEditRo ro);
}
