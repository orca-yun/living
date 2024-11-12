package ag.orca.living.core.vo.room;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomInteractVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "直播房间ID")
    private Long roomId;

    /**
     * 观众登陆提示 0 关闭 1 开启
     */
    @Schema(name = "loginTips", description = "观众登陆提示 0 关闭 1 开启")
    private Integer loginTips;

    /**
     * 全员禁言开关 0 关闭 1 开启
     */
    @Schema(name = "allMute", description = "全员禁言开关 0 关闭 1 开启")
    private Integer allMute;

    /**
     * 消息审核开关 0 关闭 1 开启
     */
    @Schema(name = "msgApprove", description = "消息审核开关 0 关闭 1 开启")
    private Integer msgApprove;

    /**
     * 免审词开关 0 关闭 1 开启
     */
    @Schema(name = "exemptEnable", description = "免审词开关 0 关闭 1 开启")
    private Integer exemptEnable;

    /**
     * 免审词
     */
    @Schema(name = "exemptWordId", description = "免审词关联ID")
    private Long exemptWordId;

    /**
     * 人数倍数开关 0 关闭 1 开启
     */
    @Schema(name = "tupleEnable", description = "人数倍数开关 0 关闭 1 开启")
    private Integer tupleEnable;

    /**
     * 人数倍数
     */
    @Schema(name = "tupleNum", description = "人数倍数")
    private Integer tupleNum;
}


