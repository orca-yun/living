package ag.orca.living.core.mongo;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomMarketGoodsItemVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GoodsActionEventRecord extends BaseBean {

    @Id
    private String id;


    private Long roomId;

    /**
     * 推荐的商品
     */
    private LivingRoomMarketGoodsItemVo recommend;

    /**
     * 所有上架的商品
     */
    private List<LivingRoomMarketGoodsItemVo> items;


    @Indexed(background = true)
    private Long cts;

    @Indexed(background = true)
    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}
