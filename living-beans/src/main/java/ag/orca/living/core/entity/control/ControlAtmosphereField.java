package ag.orca.living.core.entity.control;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 氛围场控
 *
 * @TableName t_atmosphere_field_control
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlAtmosphereField extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 礼物内容
     */
    private String giftContent;

    /**
     * 发送数量
     */
    private Integer quantity;

    /**
     * 文本消息发送间隔, 单位: 秒
     */
    private BigDecimal textInterval;

    /**
     * 礼物消息发送间隔, 单位: 秒
     */
    private BigDecimal giftInterval;

    /**
     * 执行状态, 0: 已停止, 1: 已启动
     */
    private Integer execStatus;

    /**
     * 预计结束时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expEndTime;

}