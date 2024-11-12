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
 * 渠道日数据表
 *
 * @TableName t_day_channel_data
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DayChannelData extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 机构id
     */
    private Long orgId;

    /**
     * 渠道ID 目前是6位数字
     */
    private Long channelId;

    /**
     * 当日的渠道名称
     */
    private String channelName;

    /**
     * 渠道日引流人数
     */
    private Integer flowPeople;

    /**
     * 渠道引流日交易金额(分)
     */
    private Long transactionAmount;

    /**
     * 渠道当日分佣比例
     */
    private Integer commissionRatio;

    /**
     * 渠道日分佣金额(分)
     */
    private Long subCommission;

    /**
     * 日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate day;

}
