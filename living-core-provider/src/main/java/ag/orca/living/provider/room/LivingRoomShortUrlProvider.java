package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomShortUrlService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.E2eConfig;
import ag.orca.living.core.entity.room.LivingRoomShortUrl;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.room.LivingRoomShortUrlRepo;
import ag.orca.living.core.repo.room.manage.LivingRoomManage;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class LivingRoomShortUrlProvider implements LivingRoomShortUrlService {

    @Resource
    LivingRoomShortUrlRepo roomShortUrlRepo;

    @Resource
    LivingRoomManage livingRoomManage;

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    E2eConfig e2eConfig;

    @Override
    public String findRedirectUrl(String randomStr) {
        log.info("randomStr={}", randomStr);
        LivingRoomShortUrl shortUrl = roomShortUrlRepo.findRoomShortUrlByRandomStr(randomStr);
        OrcaAssert.match(Objects.isNull(shortUrl), I18nUtil.getMessage("short.url.error"));
        Pair<String, String> m = livingRoomManage.encryptToKey(shortUrl.getRoomId(), shortUrl.getChannelId());
        String key = m.getLeft();
        String random = m.getRight();
        OrcaAssert.match(!Objects.equals(random, randomStr), I18nUtil.getMessage("short.url.not.match"));
        Optional<Long> optional = livingLiveRepo.getLivingRecordIdByRoomId(shortUrl.getRoomId());
        if (optional.isEmpty()) {
            return MessageFormat.format(e2eConfig.getSharePlayFormat(), key);
        }
        return MessageFormat.format(e2eConfig.getShareLiveFormat(), key);
    }
}
