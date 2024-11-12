package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import ag.orca.living.core.enums.BoolEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgAdmin extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 机构账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 0 锁定 1 正常
     */
    @Builder.Default
    private Integer status = BoolEnum.TRUE.getCode();

}
