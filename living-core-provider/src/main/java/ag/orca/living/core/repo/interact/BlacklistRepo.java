package ag.orca.living.core.repo.interact;

import ag.orca.living.core.bo.BlacklistUserInfo;
import ag.orca.living.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ag.orca.living.common.CacheConst.BLACKLIST_PREFIX;
import static ag.orca.living.common.CacheConst.BLACKLIST_UID_PREFIX;

@Repository
public class BlacklistRepo extends AbstractInteractRepo {


    public List<BlacklistUserInfo> findAllBlacklist(Long roomId) {
        Set<String> stringSet = stringRedisTemplate.opsForZSet().range(buildKey(roomId), 0L, -1L);
        return CollectionUtils.isEmpty(stringSet)
                ? new ArrayList<>()
                : stringSet.stream().map(s -> JsonUtil.jsonToBean(s, BlacklistUserInfo.class)).collect(Collectors.toList());
    }

    public void joinUserToBlacklist(Long roomId, BlacklistUserInfo info) {
        stringRedisTemplate.opsForZSet().add(buildKey(roomId), JsonUtil.beanToJson(info), 1);
        stringRedisTemplate.opsForSet().add(BLACKLIST_UID_PREFIX + roomId, info.getUid());
    }

    public void removeUserFromBlacklist(Long roomId, BlacklistUserInfo info) {
        stringRedisTemplate.opsForZSet().remove(buildKey(roomId), JsonUtil.beanToJson(info));
        stringRedisTemplate.opsForSet().remove(BLACKLIST_UID_PREFIX + roomId, info.getUid());
    }

    @Override
    public String getPrefix() {
        return BLACKLIST_PREFIX;
    }


    public void cleanBlacklist(Long roomId) {
        // 清理黑名单信息
        stringRedisTemplate.delete(BLACKLIST_PREFIX + roomId);
        stringRedisTemplate.delete(BLACKLIST_UID_PREFIX + roomId);
    }

    public Boolean isInBlacklist(Long roomId, String uid) {
        return stringRedisTemplate.opsForSet().isMember(BLACKLIST_UID_PREFIX + roomId, uid);
    }
}
