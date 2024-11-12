package ag.orca.living.core.repo.share;

import ag.orca.living.core.dao.share.LivingShareUserViewRecordMapper;
import ag.orca.living.core.entity.share.LivingShareUserViewRecord;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class LivingShareUserViewRecordRepo {

    @Resource
    LivingShareUserViewRecordMapper livingShareUserViewRecordMapper;


    public List<LivingShareUserViewRecord> findListByRoomIdAndViewDateAndChannelIds(Long roomId,
                                                                                    LocalDate viewDate,
                                                                                    List<Long> channelIds) {
        return livingShareUserViewRecordMapper.findListByRoomIdAndViewDateAndChannelIds(roomId, viewDate, channelIds);
    }
}
