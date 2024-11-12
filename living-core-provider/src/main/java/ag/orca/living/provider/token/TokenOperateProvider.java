package ag.orca.living.provider.token;

import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.auth.CrossAuth;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.core.convert.AuthInfoConvert;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.repo.token.TokenOperateRepo;
import ag.orca.living.core.ro.auth.ShareAuthUserRo;
import ag.orca.living.core.ro.login.CrossLoginRo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Optional;

@Slf4j
@DubboService
public class TokenOperateProvider implements TokenOperateService {

    @Resource
    TokenOperateRepo tokenOperateRepo;


    @Override
    public void removeAuthInfoByCross(Long roomId, String uid, CrossRoleEnum role) {
        tokenOperateRepo.removeAuthInfo(roomId, uid, role);
    }

    @Override
    public void addAuthInfoByCrossAuth(Long roomId, String uid, CrossAuth auth, CrossLoginRo ro, int days) {
        AuthInfo authInfo = AuthInfoConvert.crossToAuthInfo(auth, ro);
        tokenOperateRepo.addAndExpireAuthInfo(roomId, uid, CrossRoleEnum.ofCode(auth.getRole()), authInfo, days);
    }

    @Override
    public void addAuthInfoByShareAuth(Long roomId, String uid, ShareAuth auth, ShareAuthUserRo ro, String loginToken, int days) {
        AuthInfo authInfo = AuthInfoConvert.shareToAuthInfo(auth, ro);
        tokenOperateRepo.addAndExpireAuthInfo(roomId, uid, CrossRoleEnum.ofCode(auth.getRole()), authInfo, days);
        tokenOperateRepo.addAndExpireShareLoginToken(uid, loginToken, days);
    }

    @Override
    public String getShareLoginTokenByUid(String uid) {
        return tokenOperateRepo.getShareLoginTokenByUid(uid);
    }

    @Override
    public Optional<AuthInfo> getAuthInfoByShareAuth(ShareAuth auth) {
        return tokenOperateRepo.getAuthInfo(auth.getRoomId(), auth.getUid(), CrossRoleEnum.ofCode(auth.getRole()));
    }


    @Override
    public Optional<AuthInfo> getAuthInfoByCrossAuth(CrossAuth auth) {
        return tokenOperateRepo.getAuthInfo(auth.getRoomId(), auth.getUid(), CrossRoleEnum.ofCode(auth.getRole()));
    }

    @Override
    public void delayAuth(AuthInfo auth, int days) {
        tokenOperateRepo.expireAuthInfo(auth.getRoomId(), auth.getUid(), CrossRoleEnum.ofCode(auth.getRole()), days);
    }


}
