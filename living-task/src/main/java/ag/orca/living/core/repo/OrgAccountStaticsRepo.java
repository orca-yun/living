package ag.orca.living.core.repo;

import ag.orca.living.core.convert.OrgAccountRecordConvert;
import ag.orca.living.core.dao.org.OrgAccountMapper;
import ag.orca.living.core.dao.org.OrgAccountRecordMapper;
import ag.orca.living.core.dao.share.LivingShareUserViewRecordMapper;
import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.entity.org.OrgAccountRecord;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.enums.AccountRecordTypeEnum;
import ag.orca.living.core.enums.BizOperateNoEnum;
import ag.orca.living.util.NumberUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ag.orca.living.common.OrcaConst.DATE_FORMAT;

@Repository
public class OrgAccountStaticsRepo {

    @Resource
    LivingShareUserViewRecordMapper livingShareUserViewRecordMapper;

    @Resource
    OrgAccountMapper accountMapper;

    @Resource
    OrgAccountRecordMapper accountRecordMapper;


    @Transactional(rollbackFor = Exception.class)
    public void processOrgAccount(LocalDateTime trigger, Organization r) {
        // 直播数据扣费
        processLivingBill(r, trigger.toLocalDate().minusDays(1));
        // todo 点播数据扣费
        // todo 存储数据扣费
    }

    private void processLivingBill(Organization organization, LocalDate date) {
        Long cnt = livingShareUserViewRecordMapper.countUserCntByOrgIdAndViewDate(organization.getId(), date);
        if (cnt > 0) {
            OrgAccount account = accountMapper.findFirstByOrgId(organization.getId());
            Long beforeAmount = account.getBalance();
            Long minusAmount = organization.getLivingPrice() * cnt;
            Long afterAmount = beforeAmount - minusAmount;
            BizOperateNoEnum operateNoEnum = BizOperateNoEnum.LIVING_CONSUME;
            String recordName = date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
                    + operateNoEnum.getDescribe()
                    + NumberUtil.toDouble(minusAmount);
            OrgAccountRecord record = OrgAccountRecordConvert.buildEntity(organization, account,
                    beforeAmount, minusAmount, afterAmount,
                    BizOperateNoEnum.LIVING_CONSUME, AccountRecordTypeEnum.CONSUME, recordName);
            accountRecordMapper.insert(record);
            accountMapper.debitBalance(organization.getId(), afterAmount, LocalDateTime.now());
        }
    }
}
