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
public class AuthInfo extends BaseBean {

    private Long orgId;

    private Long roomId;

    private Integer role;

    private String uid;

    private String key;

    private String account;

    private String nickname;

    private String headIco;

    private Long channelId;

    /**
     * 管理端
     *
     * @param auth
     */
    public AuthInfo(AdminAuth auth) {
        this.orgId = auth.getOrgId();
        this.account = auth.getAccount();
    }

}
