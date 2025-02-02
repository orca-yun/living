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
public class CrossAuth extends BaseBean {
    private Long orgId;

    private Long roomId;

    private Integer role;

    private String uid;

    private String key;

    public CrossAuth(Long orgId, Long roomId, Integer role, String uid, String key) {
        this.orgId = orgId;
        this.roomId = roomId;
        this.role = role;
        this.uid = uid;
        this.key = key;
    }
}
