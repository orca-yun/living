package ag.orca.living.core.vo.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrgAccountRecordVo extends BaseBean {

    @Schema(name = "id", description = "ID")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 账户ID
     */
    @Schema(name = "accountId", description = "账户ID")
    private Long accountId;

    /**
     * 流水记录NO
     */
    @Schema(name = "recordNo", description = "流水记录NO")
    private String recordNo;

    /**
     * 业务记录编号
     */
    @Schema(name = "bizNo", description = "业务记录编号")
    private String bizNo;

    /**
     * 变更前余额
     */
    @Schema(name = "beforeBalance", description = "变更前余额")
    private Long beforeBalance;

    /**
     * 变更后余额
     */
    @Schema(name = "afterBalance", description = "变更后余额")
    private Long afterBalance;

    /**
     * 变更金额
     */
    @Schema(name = "amount", description = "变更金额")
    private Long amount;

    /**
     * 记录类型 1 充值 2 扣费
     */
    @Schema(name = "recordType", description = "记录类型 1 充值 2 扣费")
    private Integer recordType;
}
