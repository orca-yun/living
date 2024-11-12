package ag.orca.living.biz;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.config.JwtConfig;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.util.EncryptUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static ag.orca.living.common.OrcaConst.SPLIT;

@Slf4j
@Getter
@Service
public class TokenService {

    @Resource
    JwtConfig jwtConfig;

    @DubboReference
    LivingSeqService seqService;

    @DubboReference
    TokenOperateService tokenOperateService;

    private String buildPlain(Long orgId, Long roomId, int role, String uid) {
        return orgId + SPLIT + roomId + SPLIT + role + SPLIT + uid;
    }

    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        token = prepareToken(token);
        boolean expire = isExpired(token);
        if (expire) {
            return false;
        }
        // 验证内容
        ShareAuth authInfo = parseToken(token);
        return StringUtils.equals(EncryptUtil.desDecrypt(authInfo.getKey()),
                buildPlain(authInfo.getOrgId(), authInfo.getRoomId(), authInfo.getRole(), authInfo.getUid()));

    }

    private String prepareToken(String token) {
        String prefix = jwtConfig.getTokenPrefix();
        if (token.startsWith(prefix)) {
            token = token.replace(prefix, "").trim();
        }
        return token;
    }

    public ShareAuth parseToken(String token) {
        token = prepareToken(token);
        DecodedJWT jwt = extractClaims(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Long orgId = claimMap.get("orgId").asLong();
        Long roomId = claimMap.get("roomId").asLong();
        Integer role = claimMap.get("role").asInt();
        String uid = claimMap.get("uid").asString();
        String key = claimMap.get("key").asString();
        Long channelId = claimMap.get("channelId").asLong();
        return new ShareAuth(orgId, roomId, role, uid, key, channelId);
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, DecodedJWT::getExpiresAt);
    }

    private <T> T extractClaim(String token, Function<DecodedJWT, T> resolver) {
        DecodedJWT jwt = extractClaims(token);
        return resolver.apply(jwt);
    }

    private DecodedJWT extractClaims(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        return verifier.verify(token);
    }

}
