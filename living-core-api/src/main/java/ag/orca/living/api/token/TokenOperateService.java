package ag.orca.living.api.token;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.auth.CrossAuth;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.ro.auth.ShareAuthUserRo;
import ag.orca.living.core.ro.login.CrossLoginRo;

import java.util.Optional;

public interface TokenOperateService {
    void addAuthInfoByCrossAuth(Long roomId,
                                String uid,
                                CrossAuth crossAuth,
                                CrossLoginRo ro, int days);

    Optional<AuthInfo> getAuthInfoByCrossAuth(CrossAuth auth);

    void delayAuth(AuthInfo s, int days);

    void addAuthInfoByShareAuth(Long roomId,
                                String uid,
                                ShareAuth shareAuth,
                                ShareAuthUserRo ro,
                                String loginToken,
                                int days);

    String getShareLoginTokenByUid(String uid);

    Optional<AuthInfo> getAuthInfoByShareAuth(ShareAuth auth);

    void removeAuthInfoByCross(Long roomId, String uid, CrossRoleEnum role);


}
