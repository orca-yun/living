package ag.orca.living.core.repo.room;

import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;

public abstract class AbstractObjCacheRepo<T> {
    @Resource
    public StringRedisTemplate stringRedisTemplate;

    protected abstract String getPrefix();

    protected String buildKey(Long id) {
        return getPrefix() + id;
    }

    protected abstract T getFromDb(Long id);

    protected abstract Class<T> clazz();

    protected void refreshCache(Long id) {
        stringRedisTemplate.delete(buildKey(id));
        getCache(id);
    }

    public T getCache(Long id) {
        String k = buildKey(id);
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(k))) {
            String cache = stringRedisTemplate.opsForValue().get(k);
            if (StringUtils.isNotBlank(cache)) {
                return JsonUtil.jsonToBean(cache, clazz());
            }
        }
        T t = getFromDb(id);
        if (Objects.nonNull(t)) {
            stringRedisTemplate.opsForValue().set(k, JsonUtil.beanToJson(t));
        }
        return t;
    }
}
