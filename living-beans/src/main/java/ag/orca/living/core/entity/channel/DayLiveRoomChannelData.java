package ag.orca.living.core.entity.channel;

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

import java.time.LocalDate;

/**
 * 直播间渠道日数据表
 *
 * @TableName t_day_living_room_channel_data
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DayLiveRoomChannelData extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 直播间id
     */
    private Long roomId;

    /**
     * 直播历史记录id
     */
    private Long liveRecordId;

    /**
     * 渠道ID 目前是6位数字
     */
    private Long channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道为直播间引流人数
     */
    private Integer flowPeople;

    /**
     * 渠道引流人在直播房间交易金额(分)
     */
    private Long transactionAmount;

    /**
     * 渠道当如分佣比例
     */
    private Integer commissionRatio;

    /**
     * 渠道在直播间分佣金额(分)
     */
    private Long subCommission;

    /**
     * 日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate day;


}
