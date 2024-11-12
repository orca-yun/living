package ag.orca.living.core.vo.channel;

import ag.orca.living.core.BaseBean;
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

/**
 * 渠道引流直播间数据
 *
 * 
 * @date 2024-04-27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道引流直播间数据")
public class ChannelRoomDetailVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "自增ID")
    private Long id;

    /**
     * 直播间id
     */
    @Schema(title = "直播间id")
    private Long roomId;

    /**
     * 直播间记录id
     */
    @Schema(title = "直播记录id")
    private Long liveRecordId;

    /**
     * 直播名称
     */
    @Schema(title = "直播名称")
    private String roomName;

    /**
     * 开始直播时间
     */
    @Schema(name = "beginTime", description = "开始直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束直播时间
     */
    @Schema(name = "endTime", description = "结束直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime;


    @Schema(title = "引入流人数")
    private Integer flowPeople;

    /**
     * 成交金额(分)
     */
    @Schema(title = "成交金额(分)")
    private Long transactionAmount;

    /**
     * 分佣金额(分)
     */
    @Schema(title = "分佣金额(分)")
    private Long subCommission;

    /**
     * 分佣比例 百分数1-100
     */
    @Schema(title = "分佣比例")
    private Integer commissionRatio;


}
