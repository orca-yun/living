package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgAccountRecord extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 账户ID
     */
    private Long accountId;


    /**
     * 事件事件
     */
    private LocalDateTime eventTime;

    /**
     * 流水记录NO
     */
    private String recordNo;

    /**
     * 业务记录编号
     */
    private String bizNo;

    /**
     * 变更前余额
     */
    private Long beforeBalance;

    /**
     * 变更后余额
     */
    private Long afterBalance;

    /**
     * 变更金额
     */
    private Long amount;

    /**
     * 记录类型 1 充值 2 扣费
     */
    private Integer recordType;


    /**
     * 记录备注
     */
    private String remark;

    /**
     * 记录名称
     */
    private String recordName;

}
