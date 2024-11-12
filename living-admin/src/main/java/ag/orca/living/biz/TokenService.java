package ag.orca.living.biz;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.JwtConfig;
import ag.orca.living.core.auth.AdminAuth;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static ag.orca.living.common.OrcaConst.DAY_MILL;
import static ag.orca.living.common.OrcaConst.SPLIT;

@Getter
@Service
public class TokenService {

    @Resource
    JwtConfig jwtConfig;

    private String buildPlain(Long orgId, String account) {
        return orgId + SPLIT + account;
    }

    public String generateToken(OrgAdmin admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("orgId", admin.getOrgId());
        claims.put("account", admin.getAccount());
        String plain = buildPlain(admin.getOrgId(), admin.getAccount());
        String key = EncryptUtil.desEncrypt(plain);
        OrcaAssert.match(Objects.isNull(key), I18nUtil.getMessage("des.encrypt.err"));
        claims.put("key", key);
        return generateToken(claims);
    }

    private String prepareToken(String token) {
        String prefix = jwtConfig.getTokenPrefix();
        if (token.startsWith(prefix)) {
            token = token.replace(prefix, "").trim();
        }
        return token;
    }

    public AdminAuth parseToken(String token) {
        token = prepareToken(token);
        DecodedJWT jwt = extractClaims(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Long orgId = claimMap.get("orgId").asLong();
        String account = claimMap.get("account").asString();
        String key = claimMap.get("key").asString();
        return new AdminAuth(orgId, key, account);
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
        AdminAuth info = parseToken(token);
        return StringUtils.equals(EncryptUtil.desDecrypt(info.getKey()), buildPlain(info.getOrgId(), info.getAccount()));

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

    private String generateToken(Map<String, Object> claims) {
        JWTCreator.Builder builder = JWT.create();
        long expire = jwtConfig.getExpirationTime() * DAY_MILL;
        return builder.withPayload(claims)
                .withSubject("server")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
    }


}
