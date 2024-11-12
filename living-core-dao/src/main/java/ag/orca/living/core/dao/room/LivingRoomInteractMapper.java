package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoomInteract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LivingRoomInteractMapper {

    LivingRoomInteract findLivingRoomInteractByRoomId(@Param("roomId") Long roomId);


    int insert(LivingRoomInteract livingRoomInteract);


    int update(LivingRoomInteract livingRoomInteract);

    int countExemptWithUsed(@Param("exemptWordIds") List<Long> exemptWordIds);
}
