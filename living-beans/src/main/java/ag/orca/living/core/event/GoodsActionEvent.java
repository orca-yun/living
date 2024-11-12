package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class GoodsActionEvent extends BaseBean {

    private Long roomId;

    /**
     * 推荐的商品
     */
    private LivingRoomMarketGoodsItemVo recommend;

    /**
     * 所有上架的商品
     */
    private List<LivingRoomMarketGoodsItemVo> items;
}
