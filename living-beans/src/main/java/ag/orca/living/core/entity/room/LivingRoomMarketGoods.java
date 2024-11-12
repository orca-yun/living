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
public class LivingRoomMarketGoods extends BaseEntity {


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
     * 购买按钮描述
     */
    private String btnTxt;

    /**
     * 倒计时(秒): 0 不设置, 60 1分钟, 120 2分钟, 300 5分钟, 600 10分钟
     */
    private Integer countDown;

    /**
     * 展示样式: 1 小弹框 2 大弹框
     */
    private Integer showStyle;

}