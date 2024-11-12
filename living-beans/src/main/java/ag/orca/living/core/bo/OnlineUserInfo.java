package ag.orca.living.core.bo;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.CrossRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OnlineUserInfo extends BaseBean {
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headIco;

    /**
     * 用户ID
     */
    private Long roomId;


    /**
     * 角色
     */
    private CrossRoleEnum role;

}
