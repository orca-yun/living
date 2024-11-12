package ag.orca.living.core.vo.live;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingTriggerOplogVo extends BaseBean {

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
     * 直播开启日期
     */
    @Schema(name = "day", description = "直播开启日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

    /**
     * 调度时间
     */
    @Schema(name = "scheduleTime", description = "时间")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime scheduleTime;

    /**
     * 业务类型 1 媒体库 2 直播记录
     */
    @Schema(name = "bizType", description = "业务类型 1 媒体库 2 直播记录")
    private Integer bizType;

    /**
     * 推送任务ID
     */
    @Schema(name = "pushTaskId", description = "推流任务ID")
    private Long pushTaskId;

    /**
     * 状态 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败
     */
    @Schema(name = "status", description = "状态 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败")
    private Integer status;

    /**
     * 推流任务执行时长
     */
    @Schema(name = "duration", description = "推流任务执行时长")
    private Long duration;

    /**
     * 推流任务错误信息
     */
    @Schema(name = "msg", description = "推流任务错误信息")
    private String msg;
}
