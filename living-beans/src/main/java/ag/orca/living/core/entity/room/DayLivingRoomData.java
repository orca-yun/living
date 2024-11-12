package ag.orca.living.core.entity.room;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class DayLivingRoomData extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 机构id
     */
    private Long orgId;


    /**
     * 日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate day;


    /**
     * 直播间id
     */
    private Long roomId;


    /**
     * 直播房间交易金额(分)
     */
    private Long totalAmount;

    /**
     * 今日总共订单数
     */
    private Integer totalOderNum;

    // 订单状态 1待支付 2已支付 3已取消 4 超时未支付
    /**
     * 已支付的金额
     */
    private Long payedAmount;

    /**
     * 已支付订单数
     */
    private Integer payedOderNum;

    /**
     * 取消支付的金额(分)
     */
    private Long cancelAmount;

    /**
     * 取消支付订单数
     */
    private Integer cancelOderNum;

    /**
     * 超时未支付的金额(分)
     */
    private Long timeoutAmount;

    /**
     * 超时未支付订单数
     */
    private Integer timeoutOderNum;
}
