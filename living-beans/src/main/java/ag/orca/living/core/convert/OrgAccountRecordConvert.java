package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.entity.org.OrgAccountRecord;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.enums.AccountRecordTypeEnum;
import ag.orca.living.core.enums.BizOperateNoEnum;
import ag.orca.living.core.vo.org.OrgAccountRecordVo;
import ag.orca.living.util.NumberUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrgAccountRecordConvert {

    public static OrgAccountRecordVo entityToVo(OrgAccountRecord entity) {
        return Objects.isNull(entity) ? null :
                OrgAccountRecordVo.builder()
                        .id(entity.getId())
                        .orgId(entity.getOrgId())
                        .accountId(entity.getAccountId())
                        .recordNo(entity.getRecordNo())
                        .bizNo(entity.getBizNo())
                        .beforeBalance(entity.getBeforeBalance())
                        .afterBalance(entity.getAfterBalance())
                        .amount(entity.getAmount())
                        .recordType(entity.getRecordType())
                        .build();
    }


    public static OrgAccountRecord buildEntity(Organization organization,
                                               OrgAccount account,
                                               Long beforeAmount,
                                               Long minusAmount,
                                               Long afterAmount,
                                               BizOperateNoEnum operateNoEnum,
                                               AccountRecordTypeEnum recordTypeEnum,
                                               String recordName
    ) {

        return OrgAccountRecord.builder()
                .orgId(organization.getId())
                .accountId(account.getId())
                .eventTime(LocalDateTime.now())
                .recordNo("")
                .recordName(recordName)
                .remark(operateNoEnum.getDescribe() + NumberUtil.toDouble(minusAmount))
                .bizNo(operateNoEnum.getNo())
                .beforeBalance(beforeAmount)
                .afterBalance(afterAmount)
                .amount(minusAmount)
                .recordType(recordTypeEnum.getCode())
                .build();
    }
}
