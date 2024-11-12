package ag.orca.living.core.dao.room;

import ag.orca.living.core.entity.room.LivingRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingRoomMapper {
    LivingRoom findLivingRoomById(@Param("id") Long id);

    int insert(LivingRoom entity);

    int update(LivingRoom entity);

    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    List<LivingRoom> findListByOrgId(@Param("orgId") Long orgId);


    List<LivingRoom> findListByOrgIdAndNameLikeAndVideoType(@Param("orgId") Long orgId,
                                                            @Param("name") String name,
                                                            @Param("videoType") Integer videoType);

    List<LivingRoom> findListByIdList(@Param("ids") List<Long> ids);

    LivingRoom findSameNameRoom(@Param("orgId") Long orgId, @Param("name") String name);

    List<LivingRoom> findAllRooms();

    List<LivingRoom> findRoomListByVideoType(@Param("videoType") Integer videoType);

    int countMediaWithUsed(@Param("mediaIds") List<Long> mediaIds);

    int existsId(@Param("id") Long id);
}
