package ag.orca.living.provider.share;


import ag.orca.living.api.share.WxShareUserInfoService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.E2eConfig;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.share.WxShareUserAuthRepo;
import ag.orca.living.core.vo.wx.WxUserStateInfoVo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.text.MessageFormat;
import java.util.Optional;

import static ag.orca.living.common.OrcaConst.SPLIT;

@Slf4j
@DubboService
public class WxShareUserInfoProvider implements WxShareUserInfoService {

    @Resource
    LivingRoomRepo roomRepo;

    @Resource
    WxShareUserAuthRepo wxShareUserAuthRepo;

    @Resource
    E2eConfig e2eConfig;

    @Override
    public String buildInviteQrcode(Long roomId, Long channelId, Integer platform) {
        return wxShareUserAuthRepo.buildInviteQrcode(roomId, channelId, platform);
    }

    @Override
    public Pair<UserInfo, WxUserStateInfoVo> wxUserInfoByCodeAndState(String code, String state) {
        return wxShareUserAuthRepo.wxUserInfoByCodeAndState(code, state);
    }

    @Override
    public Optional<String> findUserIdByRandom(Long random) {
        return wxShareUserAuthRepo.findUserIdByRandom(random);
    }


    @Override
    public String redirectUrl(Long roomId, Long channelId, Long random, String token) {
        Optional<LivingRoom> optional = roomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.encode.key.error"));
        LivingRoom room = optional.get();
        String plain = room.getOrgId() + SPLIT + room.getId() + SPLIT + room.getSharePwd() + SPLIT + channelId;
        String key = EncryptUtil.desEncrypt(plain);
        return MessageFormat.format(e2eConfig.getWxRedirectFormat(), key, random + "", token);
    }

    @Override
    public String redirectPcTypeUrl() {
        return e2eConfig.getPcRedirectFormat();
    }


}
