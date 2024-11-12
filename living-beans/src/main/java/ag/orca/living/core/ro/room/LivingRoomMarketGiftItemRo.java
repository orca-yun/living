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
@SuperBuilder
@Data
@NoArgsConstructor
@Schema(name = "LivingRoomMarketGiftItemRo", description = "房间礼物项设置RO")
public class LivingRoomMarketGiftItemRo extends BaseBean {

    /**
     * 房间ID
     */
    @Schema(name = "roomId", description = "房间ID")
    @NotNull(message = "直播房间ID不能为空")
    private Long roomId;

    /**
     * 礼品库ID
     */
    @Schema(name = "giftLibIds", description = "礼品库ID列表")
    @NotNull(message = "礼品库ID列表不能为空")
    private List<Long> giftLibIds;

}
