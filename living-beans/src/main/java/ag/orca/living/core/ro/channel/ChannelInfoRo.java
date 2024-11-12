package ag.orca.living.core.ro.channel;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "ChannelInfoRo", description = "渠道配置RO")
public class ChannelInfoRo extends BaseBean {

    /**
     * ID
     */
    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 渠道名称
     */
    @Schema(name = "channelName", description = "渠道名称")
    private String channelName;

    /**
     * 渠道ID 目前是6位数字
     */
    @Schema(name = "channelId", description = "渠道ID 目前是6位数字")
    private Long channelId;

    /**
     * 渠道负责人
     */
    @Schema(name = "channelOwner", description = "渠道负责人")
    private String channelOwner;

    /**
     * 联系电话
     */
    @Schema(name = "contactPhone", description = "联系电话")
    private String contactPhone;

    /**
     * 渠道说明
     */
    @Schema(name = "channelDescription", description = "渠道说明")
    private String channelDescription;

    /**
     * 分佣比例 百分数1-100
     */
    @Schema(name = "commissionRatio", description = "分佣比例 百分数1-100")
    private Integer commissionRatio;

    /**
     * 分佣方式 1 自动分佣 2 手动分佣
     */
    @Schema(name = "commissionMethod", description = "分佣方式 1 自动分佣 2 手动分佣")
    private Integer commissionMethod;

    /**
     * 分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算
     */
    @Schema(name = "commissionPeriod", description = "分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算")
    private Integer commissionPeriod;

    /**
     * 打款银行账户 1 对公打款 2 对私打款
     */
    @Schema(name = "bankAccountType", description = "打款银行账户 1 对公打款 2 对私打款")
    private Integer bankAccountType;

    /**
     * 收款单位
     */
    @Schema(name = "receivingUnit", description = "收款单位")
    private String receivingUnit;

    /**
     * 银行账户
     */
    @Schema(name = "bankAccountNumber", description = "银行账户")
    private String bankAccountNumber;

    /**
     * 开户行
     */
    @Schema(name = "bankBranch", description = "开户行")
    private String bankBranch;

    /**
     * 结算币种 1 人民币（CNY）2 美元($)
     */
    @Builder.Default
    @Schema(name = "settlementCurrency", description = "结算币种 1 人民币（CNY）2 美元($)")
    private Integer settlementCurrency = 1;

    /**
     * account
     */
    @Schema(name = "account", description = "管理端登陆人账号")
    private String account;


}
