package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LivingRoomPageMapper {

    LivingRoomPage findLivingRoomPageByRoomId(@Param("roomId") Long roomId);


    int insert(LivingRoomPage livingRoomPage);


    int update(LivingRoomPage livingRoomPage);

}
