package ag.orca.living.biz;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.api.share.WxShareUserInfoService;
import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.JwtConfig;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.ro.auth.ShareAuthUserRo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.*;

import static ag.orca.living.common.OrcaConst.DAY_MILL;
import static ag.orca.living.common.OrcaConst.SPLIT;

@Slf4j
@Getter
@Service
public class TokenWxAuthService {

    @Resource
    JwtConfig jwtConfig;

    @DubboReference
    LivingSeqService seqService;

    @DubboReference
    TokenOperateService tokenOperateService;

    @DubboReference
    WxShareUserInfoService wxShareUserInfoService;

    private String buildPlain(Long orgId, Long roomId, int role, String uid) {
        return orgId + SPLIT + roomId + SPLIT + role + SPLIT + uid;
    }

    public Pair<String, String> generateToken(UserInfo userInfo, Long roomId, CrossRoleEnum role, Long channelId, Long orgId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("openid", userInfo.getOpenId());

        claims.put("orgId", orgId);
        claims.put("roomId", roomId);
        claims.put("role", role.getCode());
        claims.put("channelId", channelId);
        claims.put("uid", userInfo.getId());


        String plain = buildPlain(orgId, roomId, role.getCode(), userInfo.getId());
        String key = EncryptUtil.desEncrypt(plain);
        OrcaAssert.match(Objects.isNull(key), I18nUtil.getMessage("wx.des.encrypt.err"));
        claims.put("key", key);
        // 准备生成 用户
        String token = generateToken(claims);
        ShareAuth shareAuth = new ShareAuth(orgId, roomId, role.getCode(), userInfo.getId(), key, channelId);
        ShareAuthUserRo vo = ShareAuthUserRo.builder().nickname(userInfo.getNickName()).headIco(userInfo.getHeadImageUrl()).build();
        long days = jwtConfig.getExpirationTime();
        tokenOperateService.addAuthInfoByShareAuth(roomId, userInfo.getId(), shareAuth, vo, token, (int) days);
        return Pair.of(key, token);
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

    public String shareLoginTokenByRandom(Long random) {
        Optional<String> userId = wxShareUserInfoService.findUserIdByRandom(random);
        return userId.map(s -> tokenOperateService.getShareLoginTokenByUid(s)).orElse(null);
    }


}
