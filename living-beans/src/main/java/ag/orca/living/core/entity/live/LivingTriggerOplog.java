package ag.orca.living.core.entity.live;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingTriggerOplog extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 直播开启日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate day;

    /**
     * 调度时间
     */
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime scheduleTime;


    /**
     * 触发业务ID: 媒体库ID || 直播记录ID
     */
    private Long bizId;


    /**
     * 业务类型 1 媒体库 2 直播记录
     */
    private Integer bizType;


    /**
     * 推送任务ID
     */
    private Long pushTaskId;

    /**
     * 状态 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败
     */
    private Integer status;

    /**
     * 推流任务执行时长
     */
    private Long duration;

    /**
     * 推流任务错误信息
     */
    private String msg;

}
