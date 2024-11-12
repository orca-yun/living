package ag.orca.living.core.repo.interact;

import ag.orca.living.core.bo.OnlineUserInfo;
import ag.orca.living.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ag.orca.living.common.CacheConst.ASSI_ONLINE_PREFIX;

@Repository
public class AssiOnlineRepo extends AbstractInteractRepo {


    public List<OnlineUserInfo> findAllAssiOnlineList(Long roomId) {
        Set<String> stringSet = stringRedisTemplate.opsForZSet().range(buildKey(roomId), 0L, -1L);
        return CollectionUtils.isEmpty(stringSet)
                ? new ArrayList<>()
                : stringSet.stream().map(s -> JsonUtil.jsonToBean(s, OnlineUserInfo.class)).collect(Collectors.toList());
    }

    @Override
    public String getPrefix() {
        return ASSI_ONLINE_PREFIX;
    }
}