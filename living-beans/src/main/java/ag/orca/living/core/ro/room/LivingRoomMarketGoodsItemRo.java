package ag.orca.living.core.ro.room;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "LivingRoomMarketGoodsItemRo", description = "房间商品项设置RO")
public class LivingRoomMarketGoodsItemRo extends BaseBean {


    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "直播房间ID")
    @NotNull(message = "直播房间ID不能为空")
    private Long roomId;

    /**
     * 商品库ID
     */
    @Schema(name = "goodsLibIds", description = "商品库ID列表")
    @NotNull(message = "商品库ID列表不能为空")
    private List<Long> goodsLibIds;

}

