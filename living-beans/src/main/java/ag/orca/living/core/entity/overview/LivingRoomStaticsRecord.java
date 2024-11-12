package ag.orca.living.core.entity.overview;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
public class LivingRoomStaticsRecord extends BaseEntity {

    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime statTime;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 直播记录ID
     */
    private Long roomRecordId;

    /**
     * PV
     */
    private Long viewPageNum;

    /**
     * 在线人数
     */
    private Long onlineNum;

    /**
     * 离线人数
     */
    private Long offlineNum;

    /**
     * 发言人数
     */
    private Long senderNum;


    /**
     * 订单数
     */
    private Long orderNum;


    /**
     * 礼物数量
     */
    private Long giftNum;


    /**
     * 消息数量
     */
    private Long msgNum;


    /**
     * 已支付的订单数
     */
    private Long payedOrderNum;

    /**
     * 等待支付的订单
     */
    private Long waitOrderNum;

    /**
     * 放弃支付的订单
     */
    private Long cancelOrderNum;

    /**
     * 超时未支付的订单
     */
    private Long timeoutOrderNum;


}
