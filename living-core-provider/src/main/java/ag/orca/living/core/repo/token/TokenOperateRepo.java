package ag.orca.living.core.repo.token;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ag.orca.living.common.CacheConst.*;

@Repository
public class TokenOperateRepo {

    @Resource
    StringRedisTemplate stringRedisTemplate;


    private String buildKey(Long roomId, String uid, CrossRoleEnum role) {
        if (role == CrossRoleEnum.SHARE) {
            return SHARE_INFO_PREFIX + roomId + ":" + uid;
        }
        return CROSS_INFO_PREFIX + uid;
    }


    public Optional<AuthInfo> getAuthInfo(Long roomId, String uid, CrossRoleEnum role) {
        String auth = stringRedisTemplate.opsForValue().get(buildKey(roomId, uid, role));
        return Optional.ofNullable(StringUtils.isNotBlank(auth) ? JsonUtil.jsonToBean(auth, AuthInfo.class) : null);
    }

    public void removeAuthInfo(Long roomId, String uid, CrossRoleEnum role) {
        String key = buildKey(roomId, uid, role);
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            stringRedisTemplate.delete(buildKey(roomId, uid, role));
        }
    }


    public void addAndExpireAuthInfo(Long roomId, String uid, CrossRoleEnum role, AuthInfo authInfo, int days) {
        String key = buildKey(roomId, uid, role);
        stringRedisTemplate.opsForValue().set(key, JsonUtil.beanToJson(authInfo));
        stringRedisTemplate.expire(key, days, TimeUnit.DAYS);
    }

    public void expireAuthInfo(Long roomId, String uid, CrossRoleEnum role, int days) {
        String key = buildKey(roomId, uid, role);
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            stringRedisTemplate.expire(key, days, TimeUnit.DAYS);
        }
    }

    private String buildShareTokenKey(String uid) {
        return SHARE_INFO_PREFIX_TOKEN + uid;
    }

    public void addAndExpireShareLoginToken(String uid, String token, int day) {
        String key = buildShareTokenKey(uid);
        stringRedisTemplate.opsForValue().set(key, token, day, TimeUnit.DAYS);
    }

    public String getShareLoginTokenByUid(String uid) {
        String key = buildShareTokenKey(uid);
        return stringRedisTemplate.opsForValue().get(key);
    }
}
