package ag.orca.living.core.convert;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.auth.CrossAuth;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.core.ro.auth.ShareAuthUserRo;
import ag.orca.living.core.ro.login.CrossLoginRo;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class AuthInfoConvert {

    public static AuthInfo crossToAuthInfo(CrossAuth auth, CrossLoginRo ro) {
        return Objects.isNull(auth) || Objects.isNull(ro) ? null :
                AuthInfo.builder()
                        .orgId(auth.getOrgId())
                        .roomId(auth.getRoomId())
                        .role(auth.getRole())
                        .uid(auth.getUid())
                        .key(auth.getKey())
                        .nickname(ro.getNickname())
                        .headIco("")
                        .channelId(0L)
                        .build();
    }

    public static AuthInfo shareToAuthInfo(ShareAuth auth, ShareAuthUserRo ro) {
        return Objects.isNull(auth) || Objects.isNull(ro) ? null :
                AuthInfo.builder()
                        .orgId(auth.getOrgId())
                        .roomId(auth.getRoomId())
                        .role(auth.getRole())
                        .uid(auth.getUid())
                        .key(auth.getKey())
                        .nickname(ro.getNickname())
                        .headIco(StringUtils.isBlank(ro.getHeadIco()) ? "" : ro.getHeadIco())
                        .channelId(Objects.isNull(auth.getChannelId()) ? 0L : auth.getChannelId())
                        .build();
    }
}
