package ag.orca.living.core.vo.channel;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 渠道信息VO
 *
 * 
 * @date 2024-04-24
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道信息VO")
public class ChannelInfoVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "ID")
    private Long id;

    /**
     * 渠道名称
     */
    @Schema(title = "渠道名称")
    private String channelName;
    /**
     * 渠道ID 目前是6位数字
     */
    @Schema(title = "渠道ID 目前是6位数字")
    private Long channelId;

    /**
     * 渠道负责人
     */
    @Schema(title = "渠道负责人")
    private String channelOwner;

    /**
     * 联系电话
     */
    @Schema(title = "联系电话")
    private String contactPhone;

    /**
     * 分佣比例 百分数1-100
     */
    @Schema(title = "分佣比例")
    private Integer commissionRatio;

    /**
     * 分佣方式 1 自动分佣 2 手动分佣
     */
    @Schema(title = "分佣方式 1 自动分佣 2 手动分佣")
    private Integer commissionMethod;

    /**
     * 分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算
     */
    @Schema(title = "分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算")
    private Integer commissionPeriod;

    /**
     * 渠道说明
     */
    @Schema(title = "渠道说明")
    private String channelDescription;

    /**
     * 打款银行账户 1 对公打款 2 对私打款
     */
    @Schema(title = "打款银行账户 1 对公打款 2 对私打款")
    private Integer bankAccountType;

    /**
     * 收款单位
     */
    @Schema(title = "收款单位")
    private String receivingUnit;

    /**
     * 银行账户
     */
    @Schema(title = "银行账户")
    private String bankAccountNumber;

    /**
     * 开户行
     */
    @Schema(title = "开户行")
    private String bankBranch;

    /**
     * 结算币种 1 人民币（CNY）2 美元($)
     */
    @Schema(title = "结算币种 1 人民币（CNY）2 美元($)")
    private Integer settlementCurrency;


    /**
     * 是否系统内置
     */
    @Schema(title = "是否系统内置 0 非 1 是")
    private Integer sysInner;

}
