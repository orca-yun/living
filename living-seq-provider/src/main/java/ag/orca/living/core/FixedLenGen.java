package ag.orca.living.core;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FixedLenGen {

    private static final String FIXED_LEN = "orca:seq:";

    @Resource
    StringRedisTemplate stringRedisTemplate;


    public Long nextId(int len) {
        Pair<Long, Long> minMax = minMax(len);
        Long min = minMax.getLeft();
        Long max = minMax.getRight();
        String k = buildKey(len);
        Long t = stringRedisTemplate.opsForValue().increment(k);
        if (t > max) {
            stringRedisTemplate.opsForValue().set(k, min.toString());
            t = stringRedisTemplate.opsForValue().increment(k);
            return t;
        } else if (t < min) {
            stringRedisTemplate.opsForValue().set(k, min.toString());
            t = stringRedisTemplate.opsForValue().increment(k);
            return t;
        } else {
            return t;
        }
    }


    private String buildKey(int len) {
        return FIXED_LEN + len;
    }


    private static Pair<Long, Long> minMax(int len) {
        Long max = Long.parseLong("1" + String.format("%0" + len + "d", 0)) - 1;
        Long min = Long.parseLong("1" + ((len != 1) ? String.format("%0" + (len - 1) + "d", 0) : ""));
        return Pair.of(min, max);
    }


}
