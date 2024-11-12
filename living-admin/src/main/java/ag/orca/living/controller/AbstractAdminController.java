package ag.orca.living.controller;

import ag.orca.living.api.base.OrgAccountService;
import ag.orca.living.api.room.LivingRoomService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.enums.AccountStatusEnum;
import ag.orca.living.core.vo.org.OrgAccountVo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.interceptors.AuthInfoThreadLocal;
import ag.orca.living.route.OrcaController;
import ag.orca.living.util.I18nUtil;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractAdminController extends OrcaController {

    @DubboReference
    LivingRoomService livingRoomService;

    @DubboReference
    OrgAccountService orgAccountService;

    protected void checkRoom(Long roomId) {
        checkAndGetRoom(roomId);
    }

    protected Optional<LivingRoomVo> checkAndGetRoom(Long roomId) {
        Optional<LivingRoomVo> optional = livingRoomService.findById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        return optional;
    }

    protected void checkRoomId(Long pathRoomId, Long livingRoomId) {
        OrcaAssert.match(!Objects.equals(pathRoomId, livingRoomId), I18nUtil.getMessage("room.error"));
    }

    public String getAccount() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getAccount();
    }

    public void checkAccountBalance() {
        Optional<OrgAccountVo> optional = orgAccountService.findOrgAccountByOrgId(getOrgId());
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("org.account.balance.zero"));
        optional.ifPresent(o -> {
            OrcaAssert.match(o.getBalance() <= 0, I18nUtil.getMessage("org.account.balance.zero"));
            AccountStatusEnum statusEnum = AccountStatusEnum.ofCode(o.getStatus());
            OrcaAssert.match(statusEnum == AccountStatusEnum.LOCKED, I18nUtil.getMessage("org.account.balance.locked"));
        });

    }

}
