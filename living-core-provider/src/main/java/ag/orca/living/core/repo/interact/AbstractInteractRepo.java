package ag.orca.living.core.repo.interact;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;

public abstract class AbstractInteractRepo {

    @Resource
    public StringRedisTemplate stringRedisTemplate;


    public abstract String getPrefix();

    public String buildKey(Long roomId) {
        return getPrefix() + roomId;
    }

}
