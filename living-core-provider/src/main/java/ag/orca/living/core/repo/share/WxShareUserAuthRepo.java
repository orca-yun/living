package ag.orca.living.core.repo.share;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.WxConfig;
import ag.orca.living.core.convert.UserInfoConvert;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.enums.LoginTypeEnum;
import ag.orca.living.core.event.LoginRecordEvent;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.vo.wx.WxUserStateInfoVo;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
@Repository
public class WxShareUserAuthRepo {

    @Resource
    WxConfig wxConfig;

    @Resource
    WxShareUserInfoRepo wxShareUserInfoRepo;

    @Resource
    LivingRoomRepo livingRoomRepo;

    @DubboReference
    LivingSeqService livingSeqService;

    public static final String SPLICE = ",";

    public static final String ACCESS_TOKEN_FORMAT = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    public static final String USER_INFO_FORMAT = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";

    private String buildState(Long roomId, Long orgId, Long channelId, Long random, Integer platform) {
        return roomId + SPLICE + orgId + SPLICE + channelId + SPLICE + random + SPLICE + platform;
    }

    /**
     * Triple<RoomID, OrgId, ChannelId> Pair<Random, platform>
     *
     * @param state
     * @return
     */
    private WxUserStateInfoVo parseState(String state) {
        String[] parts = state.split(SPLICE);
        return UserInfoConvert.buildStateVo(parts);
    }

    public String buildInviteQrcode(Long roomId, Long channelId, Integer platform) {
        Long random = livingSeqService.nextId();
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        String state = buildState(roomId, roomOptional.get().getOrgId(), channelId, random, platform);
        String callback = URLEncoder.encode(wxConfig.getCallBack(), Charset.defaultCharset());
        return MessageFormat.format(wxConfig.getOauth2Format(), wxConfig.getAppid(), callback, state);
    }

    public Optional<String> findUserIdByRandom(Long random) {
        return wxShareUserInfoRepo.findUserIdByRandom(random);
    }

    public Pair<UserInfo, WxUserStateInfoVo> wxUserInfoByCodeAndState(String code, String state) {
        log.info("code:{}, state:{}", code, state);
        OrcaAssert.match(StringUtils.isAnyBlank(code, state), I18nUtil.getMessage("wx.login.err"));
        WxUserStateInfoVo stateInfo = parseState(state);
        Long orgId = stateInfo.getOrgId();
        Long channelId = stateInfo.getChannelId();
        Long roomId = stateInfo.getRoomId();
        //第一次就是 userId  不是的话就是随机数
        Long random = stateInfo.getRandom();
        UserInfo userInfo = buildUserInfo(code, random);
        //映射key ---> userId
        userInfo = wxShareUserInfoRepo.saveUserInfo(userInfo, channelId, orgId, roomId, random);

        Triple<Long, Long, Long> triple = Triple.of(orgId, roomId, channelId);
        LoginRecordEvent event = new LoginRecordEvent(triple, userInfo, LoginTypeEnum.LOGIN);
        log.info("[微信登录扫码 roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event));
        return Pair.of(userInfo, stateInfo);
    }


    private UserInfo buildUserInfo(String code, Long random) {
        String accessUrl = MessageFormat.format(ACCESS_TOKEN_FORMAT, wxConfig.getAppid(), wxConfig.getSecret(), code);
        JSONObject wx = httpRequest(accessUrl);
        assert wx != null;
        String userInfoUrl = MessageFormat.format(USER_INFO_FORMAT, wx.getString("access_token"), wx.getString("openid"));
        JSONObject wxUserInfo = httpRequest(userInfoUrl);
        assert wxUserInfo != null;
        return UserInfo.builder()
                .id(random.toString())
                .openId(wxUserInfo.getString("openid"))
                .nickName(wxUserInfo.getString("nickname"))
                .headImageUrl(wxUserInfo.getString("headimgurl"))
                .unionId(wxUserInfo.getString("unionid"))
                .province(wxUserInfo.getString("province"))
                .country(wxUserInfo.getString("country"))
                .city(wxUserInfo.getString("city"))
                .build();
    }

    private JSONObject httpRequest(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            //设置返回类型
            conn.setRequestProperty("contentType", "text/plain");
            //设置请求类型
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setDoOutput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


}
