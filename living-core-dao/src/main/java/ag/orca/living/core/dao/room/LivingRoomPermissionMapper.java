package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LivingRoomPermissionMapper {
    LivingRoomPermission findLivingRoomPermissionByRoomId(@Param("roomId") Long roomId);


    int insert(LivingRoomPermission livingRoomPermission);


    int update(LivingRoomPermission livingRoomPermission);


}
