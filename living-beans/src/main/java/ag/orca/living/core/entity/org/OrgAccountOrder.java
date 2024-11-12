package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgAccountOrder extends BaseEntity {

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
     * 变动金额
     */
    private Long amount;

    /**
     * 工单流水号
     */
    private String orderNo;

    /**
     * 银行流水号
     */
    private String bankOrderNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单状态 1 初始化(未支付) 2 充值失败(支付超时) 3 成功充值(支付成功) 4 订单关闭
     */
    private Integer status;

}
