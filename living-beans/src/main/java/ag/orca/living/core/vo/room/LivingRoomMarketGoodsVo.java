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
public class LivingRoomMarketGoodsVo extends BaseBean {

    /**
     * ID
     */
    @Schema(name = "id", description = "ID")
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
     * 购买按钮描述
     */
    @Schema(name = "btnTxt", description = "购买按钮描述")
    private String btnTxt;

    /**
     * 倒计时(秒)
     */
    @Schema(name = "countDown", description = "倒计时(秒): 0 不设置, 60 1分钟, 120 2分钟, 300 5分钟, 600 10分钟")
    private Integer countDown;

    /**
     * 展示样式
     */
    @Schema(name = "showStyle", description = "展示样式: 1 小弹框 2 大弹框")
    private Integer showStyle;
}

