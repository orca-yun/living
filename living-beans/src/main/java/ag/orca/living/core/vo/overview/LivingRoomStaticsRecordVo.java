package ag.orca.living.core.vo.overview;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LivingRoomStaticsRecordVo extends BaseBean {

    @Schema(name = "id", description = "ID")
    private Long id;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "statTime", description = "统计数量")
    private LocalDateTime statTime;

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
     * 直播记录ID
     */
    @Schema(name = "roomRecordId", description = "直播记录ID")
    private Long roomRecordId;

    @Schema(name = "viewPageNum", description = "总共观看次数")
    private Long viewPageNum;

    /**
     * 在线人数
     */
    @Schema(name = "onlineNum", description = "在线人数")
    private Long onlineNum;

    /**
     * 离线人数
     */
    @Schema(name = "offlineNum", description = "离线人数")
    private Long offlineNum;

    /**
     * 发言人数
     */
    @Schema(name = "senderNum", description = "发言人数")
    private Long senderNum;


    /**
     * 订单数
     */
    @Schema(name = "orderNum", description = "订单数")
    private Long orderNum;


    /**
     * 发送礼物数量
     */
    @Schema(name = "giftNum", description = "礼物数量")
    private Long giftNum;


    /**
     * 消息数量
     */
    @Schema(name = "msgNum", description = "消息数量")
    private Long msgNum;

    /**
     * 已支付的订单数
     */
    @Schema(name = "payedOrderNum", description = "已支付的订单数")
    private Long payedOrderNum;

    /**
     * 等待支付的订单
     */
    @Schema(name = "waitOrderNum", description = "等待支付的订单")
    private Long waitOrderNum;

    /**
     * 放弃支付的订单
     */
    @Schema(name = "cancelOrderNum", description = "放弃支付的订单")
    private Long cancelOrderNum;

    @Schema(name = "timeoutOrderNum", description = "超时未支付的订单")
    private Long timeoutOrderNum;

}
