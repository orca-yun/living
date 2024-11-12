package ag.orca.living.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "living")
public class LivingConfig {

    private String secretId;

    private String secretKey;

    private String region;

    private String appName;

    private String pushDomain;

    private String playDomain;

    private String key;

    private String transCode;

    private int minutes;

    private boolean playAuth;

    private String playPrefix;

    private String playKey;

    private String whipServer;


    public String buildPushRtmp(String streamName, Pair<String, String> auth) {
        return "rtmp://" + pushDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPushObsServer() {
        return "rtmp://" + pushDomain + PATH_SPLIT + appName + PATH_SPLIT;
    }

    public String buildPushObsStreamCode(String streamName, Pair<String, String> auth) {
        return streamName + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPushWebrtc(String streamName, Pair<String, String> auth) {
        return "webrtc://" + pushDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPushSrt(String streamName, Pair<String, String> auth) {
        return "srt://" + pushDomain + ":9000?streamid=#!::h=" + pushDomain + ",r=" + appName + PATH_SPLIT + streamName + "," + auth.getLeft() + "," + auth.getRight();
    }

    public String buildPushRtmpOverSrt(String streamName, Pair<String, String> auth) {
        return "rtmp://" + pushDomain + ":3570" + PATH_SPLIT + appName + PATH_SPLIT + streamName + "?" + auth.getLeft() + "&" + auth.getRight();
    }


    public String buildPlayRtmp(String streamName, Pair<String, String> auth) {
        String rtmp = "rtmp://" + playDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName;
        if (Objects.isNull(auth.getLeft()) || Objects.isNull(auth.getRight())) {
            return rtmp;
        }
        return rtmp + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPlayFlv(String streamName, Pair<String, String> auth) {
        String flv = playPrefix + "://" + playDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName + ".flv";
        if (Objects.isNull(auth.getLeft()) || Objects.isNull(auth.getRight())) {
            return flv;
        }
        return flv + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPlayM3u8(String streamName, Pair<String, String> auth) {
        String m3u8 = playPrefix + "://" + playDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName + ".m3u8";
        if (Objects.isNull(auth.getLeft()) || Objects.isNull(auth.getRight())) {
            return m3u8;
        }
        return m3u8 + "?" + auth.getLeft() + "&" + auth.getRight();
    }

    public String buildPlayWebrtc(String streamName, Pair<String, String> auth) {
        String webrtc = "webrtc://" + playDomain + PATH_SPLIT + appName + PATH_SPLIT + streamName;
        if (Objects.isNull(auth.getLeft()) || Objects.isNull(auth.getRight())) {
            return webrtc;
        }
        return webrtc + "?" + auth.getLeft() + "&" + auth.getRight();
    }


}
