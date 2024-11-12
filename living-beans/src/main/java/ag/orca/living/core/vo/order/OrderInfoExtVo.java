package ag.orca.living.core.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrderInfoExtVo extends OrderInfoVo {

    @Schema(title = "渠道名称")
    private String channelName;


    @Schema(title = "房间名称")
    private String roomName;

    /**
     * 下单商品名称
     */
    @Schema(title = "下单商品名称")
    private String goodsName;

    @Schema(title = "下单商品图片")
    private String goodsImage;

    @Schema(title = "下单商品限单类型")
    private Integer goodsBounds;
}
