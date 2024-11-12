package ag.orca.living.core.repo.stream;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.E2eConfig;
import ag.orca.living.config.LivingConfig;
import ag.orca.living.core.repo.room.manage.LivingRoomManage;
import ag.orca.living.core.repo.shortdomain.ShortDomainRepo;
import ag.orca.living.core.vo.stream.LivingE2eAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPullAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPushAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamTransPullAddressVo;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static ag.orca.living.common.OrcaConst.ZONE_OFFSET;

@Repository
public class StreamManageRepo {


    @Resource
    LivingConfig livingConfig;

    @Resource
    E2eConfig e2eConfig;

    @Resource
    ShortDomainRepo shortDomainRepo;

    @Resource
    LivingRoomManage livingRoomManage;

    private Pair<String, String> buildKey(String streamName) {
        long epochSecond = LocalDateTime.now()
                .plusMinutes(livingConfig.getMinutes())
                .toEpochSecond(ZONE_OFFSET);
        return TencentLiveSign.buildSecret(livingConfig.getKey(), streamName, epochSecond);
    }

    private Pair<String, String> buildPlayKey(String streamName) {
        if (livingConfig.isPlayAuth()) {
            long epochSecond = LocalDateTime.now()
                    .plusMinutes(livingConfig.getMinutes())
                    .toEpochSecond(ZONE_OFFSET);
            return TencentLiveSign.buildSecret(livingConfig.getPlayKey(), streamName, epochSecond);
        }
        return Pair.of(null, null);
    }


    public LivingStreamPushAddressVo buildPushAddress(Long roomId) {
        String streamName = String.valueOf(roomId);
        Pair<String, String> authKey = buildKey(streamName);
        return LivingStreamPushAddressVo.builder()
                .rtmp(livingConfig.buildPushRtmp(streamName, authKey))
                .webrtc(livingConfig.buildPushWebrtc(streamName, authKey))
                .srt(livingConfig.buildPushSrt(streamName, authKey))
                .rtmpOverSrt(livingConfig.buildPushRtmpOverSrt(streamName, authKey))
                .obsStreamCode(livingConfig.buildPushObsStreamCode(streamName, authKey))
                .obsServer(livingConfig.buildPushObsServer())
                .whipServer(livingConfig.getWhipServer())
                .build();

    }

    public LivingStreamPullAddressVo buildPullAddress(Long roomId) {
        String streamName = String.valueOf(roomId);
        Pair<String, String> authKey = buildPlayKey(streamName);
        return LivingStreamPullAddressVo.builder()
                .flv(livingConfig.buildPlayFlv(streamName, authKey))
                .rtmp(livingConfig.buildPlayRtmp(streamName, authKey))
                .webrtc(livingConfig.buildPlayWebrtc(streamName, authKey))
                .m3u8(livingConfig.buildPlayM3u8(streamName, authKey))
                .build();
    }

    public LivingStreamTransPullAddressVo buildTransPullAddress(Long roomId) {
        OrcaAssert.match(StringUtils.isBlank(livingConfig.getTransCode()), I18nUtil.getMessage("trans.template.not.exist"));
        String streamName = roomId + "_" + livingConfig.getTransCode();
        Pair<String, String> authKey = buildPlayKey(streamName);
        return LivingStreamTransPullAddressVo.builder()
                .flv(livingConfig.buildPlayFlv(streamName, authKey))
                .rtmp(livingConfig.buildPlayRtmp(streamName, authKey))
                .webrtc(livingConfig.buildPlayWebrtc(streamName, authKey))
                .m3u8(livingConfig.buildPlayM3u8(streamName, authKey))
                .build();
    }

    public LivingE2eAddressVo buildE2eAddress(Long orgId, Long roomId, Long channelId) {
        Pair<String, String> m = livingRoomManage.encryptToKey(roomId, channelId);
        String key = m.getLeft();
        String random = m.getRight();
        String assistantUrl = MessageFormat.format(e2eConfig.getAssistantFormat(), roomId.toString());
        String anchorUrl = MessageFormat.format(e2eConfig.getAnchorFormat(), roomId.toString());
        String shareUrl = MessageFormat.format(e2eConfig.getShareLiveFormat(), key);
        String randomDomain = shortDomainRepo.randomShortDomain(orgId);
        String shortUrl = MessageFormat.format(randomDomain, random);
        String playUrl = MessageFormat.format(e2eConfig.getSharePlayFormat(), key);
        return LivingE2eAddressVo.builder()
                .adminUrl(e2eConfig.getAdminFormat())
                .anchorUrl(anchorUrl)
                .assistantUrl(assistantUrl)
                .shareUrl(shareUrl)
                .shortUrl(shortUrl)
                .playUrl(playUrl)
                .build();
    }


}
