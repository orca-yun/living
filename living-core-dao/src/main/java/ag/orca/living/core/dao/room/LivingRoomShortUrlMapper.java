package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LivingRoomShortUrlMapper {

    int insert(LivingRoomShortUrl shortUrl);


    LivingRoomShortUrl findFirstByRandomStr(@Param("randomStr") String randomStr);

    LivingRoomShortUrl findFirstByRoomIdAndChannelId(@Param("roomId") Long roomId, @Param("channelId") Long channelId);
}
