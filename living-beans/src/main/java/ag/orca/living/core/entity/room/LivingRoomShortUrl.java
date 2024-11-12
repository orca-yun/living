package ag.orca.living.core.entity.room;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingRoomShortUrl extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 渠道ID
     */
    private Long channelId;


    /**
     * 短链接随机数
     */
    private String randomStr;

}
