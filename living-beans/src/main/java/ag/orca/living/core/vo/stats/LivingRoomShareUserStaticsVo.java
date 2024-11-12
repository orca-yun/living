package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用于  task  从 t_share_user_view_record 汇总后转换为  t_room_statics_record
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomShareUserStaticsVo extends BaseBean {


    @Schema(name = "viewPageNum", description = "观看页数量(PV)")
    @Builder.Default
    private Long viewPageNum = 0L;


    @Schema(name = "onlineNum", description = "在线人数(Online_UV)")
    @Builder.Default
    private Long onlineNum = 0L;

    @Schema(name = "offlineNum", description = "离线人数(Offline_UV)")
    @Builder.Default
    private Long offlineNum = 0L;


    @Schema(name = "senderNum", description = "发言人数")
    @Builder.Default
    private Long senderNum = 0L;


    @Schema(name = "giftNum", description = "礼物数")
    @Builder.Default
    private Long giftNum = 0L;


    @Schema(name = "msgNum", description = "消息数")
    @Builder.Default
    private Long msgNum = 0L;


}
