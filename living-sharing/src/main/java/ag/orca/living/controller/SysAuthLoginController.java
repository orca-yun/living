package ag.orca.living.controller;

import ag.orca.living.api.share.WxShareUserInfoService;
import ag.orca.living.biz.TokenWxAuthService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.WxLoginTypeEnum;
import ag.orca.living.core.vo.wx.WxUserStateInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/v3/auth/wx")
@Tag(name = "第三方授权登录")
public class SysAuthLoginController extends AbstractShareController {

    @DubboReference
    WxShareUserInfoService wxShareUserInfoService;

    @Resource
    TokenWxAuthService tokenWxAuthService;

    @Operation(summary = "根据roomId和channelId,生成专属分享二维码")
    @GetMapping("/qrcode")
    public OrcaResult<String> roomIdAndChannelId(@NotNull(message = "房间ID不能为空") Long roomId,
                                                 @NotNull(message = "渠道ID不能为空") Long channelId,
                                                 Integer platform) {
        return OrcaResult.success(wxShareUserInfoService.buildInviteQrcode(roomId, channelId, platform));
    }

    @Operation(summary = "微信扫码登录回调接口")
    @GetMapping("/e2e/callback")
    public ModelAndView wxCallBack(@NotBlank(message = "code不能为空") String code,
                                   @NotBlank(message = "state不能为空") String state) {
        // Triple<RoomID, OrgId, ChannelId> Pair<Random, platform>
        Pair<UserInfo, WxUserStateInfoVo> wxPair = wxShareUserInfoService.wxUserInfoByCodeAndState(code, state);
        WxUserStateInfoVo stateInfo = wxPair.getRight();
        Long roomId = stateInfo.getRoomId();
        Long orgId = stateInfo.getOrgId();
        Long channelId = stateInfo.getChannelId();
        Long random = stateInfo.getRandom();

        UserInfo userInfo = wxPair.getLeft();
        Pair<String, String> tokenPair = tokenWxAuthService.generateToken(userInfo, roomId, CrossRoleEnum.SHARE, channelId, orgId);
        String redirectUrl;
        WxLoginTypeEnum loginType = WxLoginTypeEnum.ofCode(stateInfo.getPlatform());
        if (WxLoginTypeEnum.PC == loginType) {
            redirectUrl = wxShareUserInfoService.redirectPcTypeUrl();
        } else {
            redirectUrl = wxShareUserInfoService.redirectUrl(roomId, channelId, random, tokenPair.getRight());
        }
        RedirectView redirectView = new RedirectView(redirectUrl, true, true, false);
        return new ModelAndView(redirectView);
    }

    @Operation(summary = "web端扫码登录用户token")
    @GetMapping("/e2e/{key}")
    public OrcaResult<String> webScanAuthToken(@PathVariable("key") Long random) {
        return OrcaResult.success(tokenWxAuthService.shareLoginTokenByRandom(random));
    }

}
