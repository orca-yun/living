package ag.orca.living.core.repo;

import ag.orca.living.core.dao.room.LivingRoomMapper;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.VideoTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LiveRoomRepo {

    @Resource
    LivingRoomMapper roomMapper;


    public List<LivingRoom> findAllRooms() {
        return roomMapper.findAllRooms();
    }

    public List<LivingRoom> findAllVideoRooms() {
        return roomMapper.findRoomListByVideoType(VideoTypeEnum.VIDEO.getCode());
    }
}
