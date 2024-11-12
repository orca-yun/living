package ag.orca.living.core.vo.live;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomLiveRecordVo extends BaseBean {

    @Schema(name = "id", description = "直播记录id")
    private Long id;

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
     * 开始直播日期
     */
    @Schema(name = "startDate", description = "开始直播日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 开始直播时间
     */
    @Schema(name = "beginTime", description = "开始直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 结束直播日期
     */
    @Schema(name = "stopDate", description = "结束直播日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate stopDate;

    /**
     * 结束直播时间
     */
    @Schema(name = "endTime", description = "结束直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 总共直播时长
     */
    @Schema(name = "cost", description = "总共直播时长")
    private Long cost;

    /**
     * 状态 0 直播中 1 直播结束
     */
    @Schema(name = "status", description = "状态 0 直播中 1 直播结束")
    private Integer status;

    @Schema(name = "needRecord", description = "是否需要录制 0 不需要 1 需要")
    private Integer needRecord;
}
