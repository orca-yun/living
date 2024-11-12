package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class GiftActionEvent extends BaseBean {

    private Long roomId;

    private List<LivingRoomMarketGiftItemVo> items;
}
