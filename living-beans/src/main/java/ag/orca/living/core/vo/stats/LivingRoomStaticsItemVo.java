package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomStaticsItemVo extends BaseBean {

    /**
     * 统计时刻
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "statTime", description = "统计时刻")
    private LocalDateTime statTime;

    @Schema(name = "viewPageNum", description = "总观看人数")
    @Builder.Default
    private Long viewPageNum = 0L;

    @Schema(name = "onlineNum", description = "在线人数")
    @Builder.Default
    private Long onlineNum = 0L;

    @Schema(name = "offlineNum", description = "离线人数")
    @Builder.Default
    private Long offlineNum = 0L;

    @Schema(name = "senderNum", description = "发言人数")
    @Builder.Default
    private Long senderNum = 0L;

    @Schema(name = "orderNum", description = "订单数")
    @Builder.Default
    private Long orderNum = 0L;

    @Schema(name = "giftNum", description = "礼物数量")
    @Builder.Default
    private Long giftNum = 0L;

    @Schema(name = "msgNum", description = "消息数量")
    @Builder.Default
    private Long msgNum = 0L;

    @Schema(name = "waitOrderNum", description = "等待支付的订单数")
    @Builder.Default
    private Long waitOrderNum = 0L;

    @Schema(name = "payedOrderNum", description = "已支付的订单数")
    @Builder.Default
    private Long payedOrderNum = 0L;

    @Schema(name = "cancelOrderNum", description = "取消单数")
    @Builder.Default
    private Long cancelOrderNum = 0L;

    @Schema(name = "timeoutOrderNum", description = "超时未支付单数")
    @Builder.Default
    private Long timeoutOrderNum = 0L;
}
