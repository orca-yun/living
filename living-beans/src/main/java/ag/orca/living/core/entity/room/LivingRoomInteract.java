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
public class LivingRoomInteract extends BaseEntity {

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
     * 观众登陆提示 0 关闭 1 开启
     */
    private Integer loginTips;

    /**
     * 全员禁言开关 0 关闭 1 开启
     */
    private Integer allMute;

    /**
     * 消息审核开关 0 关闭 1 开启
     */
    private Integer msgApprove;

    /**
     * 免审词开关 0 关闭 1 开启
     */
    private Integer exemptEnable;

    /**
     * 免审词 关联ID
     */
    private Long exemptWordId;


    /**
     * 人数倍数开关 0 关闭 1 开启
     */
    private Integer tupleEnable;

    /**
     * 人数倍数
     */
    private Integer tupleNum;

}
