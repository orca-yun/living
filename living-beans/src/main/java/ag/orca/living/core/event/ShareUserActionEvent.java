package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ShareUserActionEvent extends BaseBean {

    private String uid;

    /**
     * 用户ID
     */
    private Long roomId;

    /**
     * 企业ID
     */
    private Long orgId;

    /**
     * 角色
     */
    private Integer role;


    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headIco;


    /**
     * 渠道ID
     */
    private Long channelId;


    /**
     * action type  1 online 2 offline
     */
    private Integer actionType;

    /**
     * TS
     */
    private Long timestamp;
}
