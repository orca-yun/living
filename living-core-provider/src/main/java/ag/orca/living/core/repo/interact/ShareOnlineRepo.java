package ag.orca.living.core.repo.interact;

import ag.orca.living.core.bo.OnlineUserInfo;
import ag.orca.living.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ag.orca.living.common.CacheConst.SHARE_ONLINE_PREFIX;

@Repository
public class ShareOnlineRepo extends AbstractInteractRepo {


    public Long countShareOnline(Long roomId) {
        return stringRedisTemplate.opsForZSet().zCard(buildKey(roomId));
    }

    public List<OnlineUserInfo> findPageShareOnlineList(Long roomId, long start, long stop) {
        Set<String> stringSet = stringRedisTemplate.opsForZSet().range(buildKey(roomId), start, stop);
        return CollectionUtils.isEmpty(stringSet)
                ? new ArrayList<>()
                : stringSet.stream().map(s -> JsonUtil.jsonToBean(s, OnlineUserInfo.class)).collect(Collectors.toList());
    }

    @Override
    public String getPrefix() {
        return SHARE_ONLINE_PREFIX;
    }
}
