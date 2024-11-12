package ag.orca.living.controller;

import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.interceptors.AuthInfoThreadLocal;
import ag.orca.living.route.OrcaController;
import ag.orca.living.util.I18nUtil;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.Optional;

public abstract class AbstractShareController extends OrcaController {

    @DubboReference
    LivingRoomService livingRoomService;

    public Long getRoomId() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getRoomId();
    }

    public String getUid() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getUid();
    }

    public Long getUserId() {
        String uid = getUid();
        return Long.parseLong(uid);
    }

    public Long getChannelId() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getChannelId();
    }

    protected void checkRoom(Long roomId) {
        Optional<LivingRoomVo> optional = livingRoomService.findById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
    }

    public AuthInfo getAuthInfo() {
        return AuthInfoThreadLocal.get();
    }


}
