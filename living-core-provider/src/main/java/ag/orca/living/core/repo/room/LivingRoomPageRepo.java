package ag.orca.living.core.repo.room;

import ag.orca.living.core.dao.room.LivingRoomPageMapper;
import ag.orca.living.core.entity.room.LivingRoomPage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.ROOM_PAGE_PREFIX;

@Repository
public class LivingRoomPageRepo extends AbstractObjCacheRepo<LivingRoomPage> {
    @Resource
    LivingRoomPageMapper livingRoomPageMapper;

    @Override
    protected String getPrefix() {
        return ROOM_PAGE_PREFIX;
    }

    @Override
    protected LivingRoomPage getFromDb(Long id) {
        return livingRoomPageMapper.findLivingRoomPageByRoomId(id);
    }

    @Override
    protected Class<LivingRoomPage> clazz() {
        return LivingRoomPage.class;
    }

    public Optional<LivingRoomPage> findLivingRoomPageByRoomId(Long roomId) {
        return Optional.ofNullable(getCache(roomId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingRoomPage livingRoomPage) {
        Long roomId = livingRoomPage.getRoomId();
        LivingRoomPage page = livingRoomPageMapper.findLivingRoomPageByRoomId(roomId);
        if (Objects.nonNull(page)) {
            livingRoomPage.setId(page.getId());
            livingRoomPageMapper.update(livingRoomPage);
        } else {
            livingRoomPageMapper.insert(livingRoomPage);
        }
        refreshCache(roomId);
    }


}
