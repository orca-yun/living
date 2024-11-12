package ag.orca.living.core.bo;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class MuteUserInfo extends BaseBean {
    /**
     * 用户ID
     */
    private String uid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户ID
     */
    private Long roomId;

}
