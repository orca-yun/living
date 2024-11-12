package ag.orca.living.core.vo.room;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomExtendVo extends BaseBean {

    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;
    /**
     * 直播房间ID
     */
    @Schema(name = "livingRoomId", description = "直播房间ID")
    private Long livingRoomId;

    @Schema(name = "interact", description = "互动设置")
    private LivingRoomInteractVo interact;

    @Schema(name = "page", description = "页面设置")
    private LivingRoomPageVo page;

    @Schema(name = "permission", description = "权限设置")
    private LivingRoomPermissionVo permission;

    @Schema(name = "goods", description = "商品设置")
    private LivingRoomMarketGoodsVo goods;

    @Schema(name = "goodsItems", description = "商品列表")
    private List<LivingRoomMarketGoodsItemVo> goodsItems;

    @Schema(name = "giftItems", description = "礼物列表")
    private List<LivingRoomMarketGiftItemVo> giftItems;
}
