package ag.orca.living.controller;

import ag.orca.living.api.base.OrganizationService;
import ag.orca.living.api.interact.InteractBlacklistService;
import ag.orca.living.api.interact.InteractShareOnlineService;
import ag.orca.living.api.room.LivingRoomExtendService;
import ag.orca.living.biz.TokenService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.core.convert.LivingRoomShareConvert;
import ag.orca.living.core.enums.SharePrepareEnum;
import ag.orca.living.core.vo.org.OrganizationVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;
import ag.orca.living.core.vo.share.LivePrepareCheckVo;
import ag.orca.living.core.vo.share.LiveShareExtVo;
import ag.orca.living.core.vo.share.LiveShareVo;
import ag.orca.living.core.vo.stream.LivingKeyDecryptVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v3/share")
@Tag(name = "房间信息管理")
public class ShareHomeController extends AbstractShareController {

    @DubboReference
    OrganizationService organizationService;

    @DubboReference
    LivingRoomExtendService livingRoomExtendService;

    @DubboReference
    InteractShareOnlineService interactShareOnlineService;

    @DubboReference
    InteractBlacklistService interactBlacklistService;

    @Resource
    TokenService tokenService;


    @Operation(summary = "根据密钥KEY访问房间信息")
    @GetMapping("/live/{key}")
    public OrcaResult<LiveShareExtVo> keyIndex(@NotBlank(message = "key不能为空") @PathVariable("key") String key) {
        OrcaAssert.match(StringUtils.isBlank(key), I18nUtil.getMessage("room.error"));
        // 解码房间信息
        Pair<LivingKeyDecryptVo, LivingRoomShareVo> pair = livingRoomExtendService.decryptToRoomInfo(key);
        // 生产随机访问者
        LivingKeyDecryptVo vo = pair.getLeft();
        Long orgId = vo.getOrgId();
        Long roomId = vo.getRoomId();
        Long channelId = vo.getChannelId();
        Optional<OrganizationVo> optional = organizationService.findOrgByOrgId(orgId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.error"));
        LiveShareVo shareVo = LivingRoomShareConvert.build(orgId, roomId, channelId, pair.getRight(), optional.get());
        // 获取当前的房间的直播状态
        Long onlineShare = interactShareOnlineService.totalShareOnline(roomId);
        LiveShareExtVo shareExtVo = LivingRoomShareConvert.shareVoToExtVo(shareVo, onlineShare, optional.get());
        return OrcaResult.success(shareExtVo);
    }


    @Operation(summary = "预检查")
    @GetMapping("/prepare/check")
    public OrcaResult<LivePrepareCheckVo> prepareCheck(@NotBlank(message = "token不能为空")
                                                       @RequestHeader(value = "Authorization") String token) {
        LivePrepareCheckVo vo = new LivePrepareCheckVo();
        SharePrepareEnum check = SharePrepareEnum.TOKEN_ILLEGAL;
        if (StringUtils.isNotBlank(token)) {
            try {
                if (tokenService.checkToken(token)) {
                    ShareAuth authInfo = tokenService.parseToken(token);
                    boolean f = interactBlacklistService.isInBlacklist(authInfo.getRoomId(), authInfo.getUid());
                    check = f ? SharePrepareEnum.IN_BLACKLIST : SharePrepareEnum.PASS;
                } else {
                    check = SharePrepareEnum.TOKEN_EXPIRE;
                }
            } catch (Throwable e) {
                check = SharePrepareEnum.TOKEN_EXPIRE;
            }
        }
        vo.setResult(check.getCode());
        return OrcaResult.success(vo);
    }


}
