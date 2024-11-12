package ag.orca.living.biz;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.JwtConfig;
import ag.orca.living.core.auth.CrossAuth;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.ro.login.CrossLoginRo;
import ag.orca.living.core.vo.room.LivingRoomVo;
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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ag.orca.living.common.OrcaConst.DELAY_DAYS;
import static ag.orca.living.common.OrcaConst.SPLIT;

@Getter
@Service
public class TokenService {

    @Resource
    JwtConfig jwtConfig;

    @DubboReference
    LivingSeqService seqService;

    @DubboReference
    TokenOperateService tokenOperateService;


    public void removeToken(Long roomId, String uid, CrossRoleEnum role) {
        tokenOperateService.removeAuthInfoByCross(roomId, uid, role);
    }

    public String generateToken(CrossLoginRo ro, LivingRoomVo roomVo, CrossRoleEnum role) {
        String uid = seqService.uuid();
        Long roomId = roomVo.getId();
        Map<String, Object> claims = new HashMap<>();
        claims.put("orgId", roomVo.getOrgId());
        claims.put("roomId", roomId);
        claims.put("role", role.getCode());
        claims.put("uid", uid);
        String plain = buildPlain(roomVo.getOrgId(), roomId, role.getCode(), uid);
        String key = EncryptUtil.desEncrypt(plain);
        OrcaAssert.match(Objects.isNull(key), I18nUtil.getMessage("des.encrypt.err"));
        claims.put("key", key);
        String token = generateToken(claims);
        CrossAuth crossAuth = new CrossAuth(roomVo.getOrgId(), roomId, role.getCode(), uid, key);
        tokenOperateService.addAuthInfoByCrossAuth(roomId, uid, crossAuth, ro, DELAY_DAYS);
        return token;
    }

    private String buildPlain(Long orgId, Long roomId, int role, String uid) {
        return orgId + SPLIT + roomId + SPLIT + role + SPLIT + uid;
    }

    private String prepareToken(String token) {
        String prefix = jwtConfig.getTokenPrefix();
        if (token.startsWith(prefix)) {
            token = token.replace(prefix, "").trim();
        }
        return token;
    }

    public CrossAuth parseToken(String token) {
        token = prepareToken(token);
        DecodedJWT jwt = extractClaims(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        Long orgId = claimMap.get("orgId").asLong();
        Long roomId = claimMap.get("roomId").asLong();
        Integer role = claimMap.get("role").asInt();
        String uid = claimMap.get("uid").asString();
        String key = claimMap.get("key").asString();
        return new CrossAuth(orgId, roomId, role, uid, key);
    }

    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        token = prepareToken(token);
        // 验证内容
        CrossAuth authInfo = parseToken(token);
        return StringUtils.equals(EncryptUtil.desDecrypt(authInfo.getKey()),
                buildPlain(authInfo.getOrgId(), authInfo.getRoomId(),
                        authInfo.getRole(), authInfo.getUid()));

    }


    private DecodedJWT extractClaims(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        return verifier.verify(token);
    }

    private String generateToken(Map<String, Object> claims) {
        JWTCreator.Builder builder = JWT.create();
        return builder.withPayload(claims)
                .withSubject("server")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                //主播 和 助理端不设置过期时间 , 信息存储在 Redis中
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
    }


}
