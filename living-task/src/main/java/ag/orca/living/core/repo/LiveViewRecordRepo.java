package ag.orca.living.core.repo;

import ag.orca.living.core.convert.LivingShareUserViewRecordConvert;
import ag.orca.living.core.dao.share.LivingShareUserViewRecordMapper;
import ag.orca.living.core.entity.share.LivingShareUserViewRecord;
import ag.orca.living.core.event.ShareUserActionEvent;
import ag.orca.living.core.event.ShareUserStatEvent;
import ag.orca.living.core.event.UserSendStatEvent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LiveViewRecordRepo {

    @Resource
    LivingShareUserViewRecordMapper viewRecordMapper;


    @Transactional(rollbackFor = Exception.class)
    public void eventAction(ShareUserActionEvent event) {
        LivingShareUserViewRecord record = LivingShareUserViewRecordConvert.actionEventToRecord(event);
        // insertOrUpdate固定的字段
        viewRecordMapper.insertOrUpdate(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void statEvent(ShareUserStatEvent event) {
        LivingShareUserViewRecord record = LivingShareUserViewRecordConvert.reportEventToRecord(event);
        viewRecordMapper.insertOrUpdate(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void userSendStat(UserSendStatEvent event) {
        LivingShareUserViewRecord record = LivingShareUserViewRecordConvert.userSendEventToRecord(event);
        viewRecordMapper.insertOrUpdate(record);
    }
}
