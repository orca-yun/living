package ag.orca.living.core.repo.room;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.core.convert.LivingRoomShortUrlConvert;
import ag.orca.living.core.dao.room.LivingRoomShortUrlMapper;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomShortUrl;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

import static ag.orca.living.common.OrcaConst.RANDOM_MIN_MAX;
import static ag.orca.living.common.OrcaConst.SPLIT;

@Repository
public class LivingRoomShortUrlRepo {

    @Resource
    LivingRoomShortUrlMapper roomShortUrlMapper;

    @DubboReference
    LivingSeqService livingSeqService;


    public LivingRoomShortUrl findRoomShortUrlByRandomStr(String randomStr) {
        return roomShortUrlMapper.findFirstByRandomStr(randomStr);
    }

    @Transactional(rollbackFor = Exception.class)
    public LivingRoomShortUrl findOrSaveByRoomAndChannelId(LivingRoom room, Long channelId) {
        LivingRoomShortUrl shortUrl = roomShortUrlMapper.findFirstByRoomIdAndChannelId(room.getId(), channelId);
        if (Objects.nonNull(shortUrl)) {
            return shortUrl;
        }
        String r = room.getId().toString() + SPLIT + channelId.toString();
        do {
            int len = RANDOM_MIN_MAX.getLeft() +
                    new Random().nextInt(RANDOM_MIN_MAX.getRight() - RANDOM_MIN_MAX.getLeft() + 1);
            String randStr = livingSeqService.randomStrFromMd5(r, len);
            shortUrl = roomShortUrlMapper.findFirstByRandomStr(randStr);
            if (Objects.isNull(shortUrl)) {
                // 保存数据库 并返回
                LivingRoomShortUrl bean = LivingRoomShortUrlConvert.buildRoomShortUrl(room, channelId, randStr);
                roomShortUrlMapper.insert(bean);
                return bean;
            }
        } while (true);
    }


}
