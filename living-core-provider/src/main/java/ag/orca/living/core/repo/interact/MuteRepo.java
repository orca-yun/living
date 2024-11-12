package ag.orca.living.core.repo.interact;

import ag.orca.living.core.bo.MuteUserInfo;
import ag.orca.living.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ag.orca.living.common.CacheConst.MUTE_LIST_PREFIX;
import static ag.orca.living.common.CacheConst.MUTE_LIST_UID_PREFIX;

@Repository
public class MuteRepo extends AbstractInteractRepo {


    public List<MuteUserInfo> findAllMuteList(Long roomId) {
        Set<String> stringSet = stringRedisTemplate.opsForZSet().range(MUTE_LIST_PREFIX + roomId, 0L, -1L);
        return CollectionUtils.isEmpty(stringSet)
                ? new ArrayList<>()
                : stringSet.stream().map(s -> JsonUtil.jsonToBean(s, MuteUserInfo.class)).collect(Collectors.toList());
    }

    public void joinUserToMuteList(Long roomId, MuteUserInfo info) {
        stringRedisTemplate.opsForZSet().add(buildKey(roomId), JsonUtil.beanToJson(info), 1);
        stringRedisTemplate.opsForSet().add(MUTE_LIST_UID_PREFIX + roomId, info.getUid());
    }

    public void removeUserFromMuteList(Long roomId, MuteUserInfo info) {
        stringRedisTemplate.opsForZSet().remove(buildKey(roomId), JsonUtil.beanToJson(info));
        stringRedisTemplate.opsForSet().remove(MUTE_LIST_UID_PREFIX + roomId, info.getUid());
    }

    @Override
    public String getPrefix() {
        return MUTE_LIST_PREFIX;
    }


    public void cleanMuteList(Long roomId) {
        //清理禁言名单信息
        stringRedisTemplate.delete(buildKey(roomId));
        stringRedisTemplate.delete(MUTE_LIST_UID_PREFIX + roomId);
    }
}
