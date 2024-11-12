package ag.orca.living.controller;

import ag.orca.living.biz.TokenService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.ro.login.CrossLoginRo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import ag.orca.living.errors.OrcaException;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/v2/cross")
@Tag(name = "交叉登录")
public class CrossLoginController extends AbstractConsoleController {

    @Resource
    TokenService tokenService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public OrcaResult<String> login(@Validated @RequestBody CrossLoginRo ro) {
        // 根据密码判断是从哪个平台登录
        Optional<LivingRoomVo> optional = livingRoomService.findById(ro.getRoomId());
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("login.error"));
        LivingRoomVo roomVo = optional.get();
        if (StringUtils.equals(ro.getPassword(), roomVo.getAnchorPwd())) {
            return OrcaResult.success(tokenService.generateToken(ro, roomVo, CrossRoleEnum.ANCHOR));
        }
        if (StringUtils.equals(ro.getPassword(), roomVo.getAssistantPwd())) {
            return OrcaResult.success(tokenService.generateToken(ro, roomVo, CrossRoleEnum.ASSISTANT));
        }
        throw OrcaException.error(I18nUtil.getMessage("login.error"));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public OrcaResult<Void> logout() {
        AuthInfo authInfo = getAuthInfo();
        if (Objects.nonNull(authInfo)) {
            tokenService.removeToken(authInfo.getRoomId(), authInfo.getUid(), CrossRoleEnum.ofCode(authInfo.getRole()));
        }
        return OrcaResult.success();
    }

}
