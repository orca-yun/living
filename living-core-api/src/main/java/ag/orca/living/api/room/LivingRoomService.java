package ag.orca.living.api.room;

import ag.orca.living.core.ro.query.QueryRoomRo;
import ag.orca.living.core.ro.room.LivingRoomPwdRo;
import ag.orca.living.core.ro.room.LivingRoomRo;
import ag.orca.living.core.vo.room.LivingRoomVo;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LivingRoomService {

    /**
     * 分页查询
     *
     * @param orgId
     * @param ro
     * @return
     */
    Pair<List<LivingRoomVo>, Long> findPageList(Long orgId, QueryRoomRo ro);


    /**
     * 查询全部
     *
     * @param orgId
     * @return
     */
    List<LivingRoomVo> findList(Long orgId);

    void save(LivingRoomRo ro);

    void edit(LivingRoomRo ro);

    void remove(Long orgId, List<Long> ids);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Optional<LivingRoomVo> findById(Long id);


    List<LivingRoomVo> findLatestTopLivingRoom(Long orgId, LocalDate now);


    void modRoomPwd(Long roomId, LivingRoomPwdRo ro);

    List<LivingRoomVo> findListByIdList(List<Long> roomIds);
}
