package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.enums.LoginTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.tuple.Triple;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LoginRecordEvent extends BaseBean {

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 机构id
     */
    private Long orgId;

    /**
     * 直播间id
     */
    private Long roomId;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 用户id
     */
    private String userId;


    /**
     * 登录方式，1 扫码登录 2 缓存登录
     */
    private int type;

    public LoginRecordEvent(Triple<Long, Long, Long> triple,
                            UserInfo userInfo,
                            LoginTypeEnum type) {
        this.orgId = triple.getLeft();
        this.roomId = triple.getMiddle();
        this.channelId = triple.getRight();

        this.openId = userInfo.getOpenId();
        this.userId = userInfo.getId();
        this.type = type.getCode();
    }
}
