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
public class OrgAccountVo extends BaseBean {

    @Schema(name = "id", description = "ID")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 账户余额
     */
    @Schema(name = "balance", description = "账户余额 分")
    private Long balance;

    /**
     * 账户状态 1 正常 2 冻结
     */
    @Schema(name = "status", description = "账户状态 1 正常 2 冻结")
    private Integer status;
}
