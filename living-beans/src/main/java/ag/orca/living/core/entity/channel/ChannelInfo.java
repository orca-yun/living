package ag.orca.living.core.entity.channel;

import ag.orca.living.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 渠道信息表
 *
 * @TableName t_channel_info
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChannelInfo extends BaseEntity {

    /**
     * ID
     */
    private Long id;
    /**
     * orgId
     */
    private Long orgId;
    /**
     * 渠道名称
     */
    private String channelName;
    /**
     * 渠道ID 目前是6位数字
     */
    private Long channelId;
    /**
     * 渠道负责人
     */
    private String channelOwner;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 渠道说明
     */
    private String channelDescription;
    /**
     * 分佣比例 百分数1-100
     */
    private Integer commissionRatio;
    /**
     * 支付手续费
     */
    private Integer paymentFee;
    /**
     * 准备的分佣比例 百分数1-100（下一天生效）
     */
    private Integer prepareCommissionRatio;
    /**
     * 分佣方式 1 自动分佣 2 手动分佣
     */
    private Integer commissionMethod;
    /**
     * 分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算
     */
    private Integer commissionPeriod;
    /**
     * 打款银行账户 1 对公打款 2 对私打款
     */
    private Integer bankAccountType;
    /**
     * 收款单位
     */
    private String receivingUnit;
    /**
     * 银行账户
     */
    private String bankAccountNumber;
    /**
     * 开户行
     */
    private String bankBranch;
    /**
     * 结算币种 1 人民币（CNY）2 美元($)
     */
    private Integer settlementCurrency;
    /**
     * 创建人账号
     */
    private String createUser;
    /**
     * 修改人账号
     */
    private String updateUser;

    /**
     * 是否系统内置
     */
    private Integer sysInner;

}
