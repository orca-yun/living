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
public class ShareUserStatEvent extends BaseBean {

    /**
     * 时间戳
     */
    private long ts;

    /**
     * 唯一ID 指令的唯一ID
     */
    private String uuid;

    /**
     * 企业ID
     */
    private Long orgId;


    /**
     * 渠道ID
     */
    private Long channelId;

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

    /**
     * 头像
     */
    private String headIco;


    /**
     * 房间直播记录ID
     */
    private Long roomRecordId;

    /**
     * 上报的时间
     */
    private Long timestamp;

    /**
     * UA
     */
    private String userAgent;


    private String secChUa;

    private String secChUaMobile;

    private String secChUaPlatform;


}
