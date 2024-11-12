package ag.orca.living.api.share;

import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.vo.wx.WxUserStateInfoVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public interface WxShareUserInfoService {

    /**
     * 构造渠道分享直播间的二维码
     *
     * @param roomId    房间ID
     * @param channelId 渠道ID
     * @param platform  登录方式 1 pc端 2 手机端
     * @return String 图片二维码
     */
    String buildInviteQrcode(Long roomId, Long channelId, Integer platform);

    /**
     * 同code换取access_token
     *
     * @param code  房间ID
     * @param state 渠道ID
     */
    Pair<UserInfo, WxUserStateInfoVo> wxUserInfoByCodeAndState(String code, String state);

    /**
     * 手机端获取重定向url
     */
    String redirectUrl(Long roomId, Long channelId, Long random, String token);

    /**
     * web端登录重定向地址
     */
    String redirectPcTypeUrl();

    Optional<String> findUserIdByRandom(Long random);

}
