package ag.orca.living.core.vo.channel;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 数据详情
 *
 * 
 * @date 2024-04-27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据详情")
public class ChannelDataDetailVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "自增ID")
    private Long id;

    /**
     * 渠道ID 目前是6位数字
     */
    @Schema(title = "渠道ID 目前是6位数字")
    private Long channelId;

    /**
     * 渠道名称
     */
    @Schema(title = "渠道名称")
    private String channelName;

    /**
     * 引流人数
     */
    @Schema(title = "引流人数")
    @Builder.Default
    private Integer flowPeople = 0;

    /**
     * 成交金额(分)
     */
    @Schema(title = "成交金额(分)")
    @Builder.Default
    private Long transactionAmount = 0L;

    /**
     * 分佣金额(分)
     */
    @Schema(title = "分佣金额(分)")
    @Builder.Default
    private Long subCommission = 0L;

    /**
     * 分佣比例 百分数1-100
     */
    @Schema(title = "分佣比例")
    @Builder.Default
    private Integer commissionRatio = 0;

    /**
     * 日期
     */
    @Schema(title = "日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

}
