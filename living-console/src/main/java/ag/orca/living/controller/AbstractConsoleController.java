package ag.orca.living.controller;

import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.interceptors.AuthInfoThreadLocal;
import ag.orca.living.route.OrcaController;
import ag.orca.living.util.I18nUtil;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.Objects;
import java.util.Optional;


public abstract class AbstractConsoleController extends OrcaController {

    @DubboReference
    LivingRoomService livingRoomService;


    public AuthInfo getAuthInfo() {
        return AuthInfoThreadLocal.get();
    }

    public Long getRoomId() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getRoomId();
    }

    public String getNickname() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getNickname();
    }

    public String getUid() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getUid();
    }

    public void checkRoomId(Long roomId) {
        Long headRoomId = getRoomId();
        OrcaAssert.match(!Objects.equals(roomId, headRoomId), I18nUtil.getMessage("room.error"));
    }

    protected void checkRoom(Long roomId) {
        checkAndGetRoom(roomId);
    }

    protected Optional<LivingRoomVo> checkAndGetRoom(Long roomId) {
        checkRoomId(roomId);
        Optional<LivingRoomVo> optional = livingRoomService.findById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        return optional;
    }
}
