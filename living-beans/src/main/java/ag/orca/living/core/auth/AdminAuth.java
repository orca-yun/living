package ag.orca.living.core.auth;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class AdminAuth extends BaseBean {

    private Long orgId;

    private String key;

    private String account;


    /**
     * 管理端
     *
     * @param orgId
     * @param key
     * @param account
     */
    public AdminAuth(Long orgId, String key, String account) {
        this.orgId = orgId;
        this.key = key;
        this.account = account;
    }
}
