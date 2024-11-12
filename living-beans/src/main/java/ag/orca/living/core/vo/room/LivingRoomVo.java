package ag.orca.living.core.vo.room;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 直播房间名称
     */
    @Schema(name = "name", description = "直播房间名称")
    private String name;


    /**
     * 主播密码
     */
    @Schema(name = "name", description = "主播密码")
    private String anchorPwd;

    /**
     * 助理密码
     */
    @Schema(name = "name", description = "助理密码")
    private String assistantPwd;

    /**
     * 用户密码
     */
    @Schema(name = "sharePwd", description = "用户密码")
    private String sharePwd;

    /**
     * 直播时间
     */
    @Schema(name = "livingTime", description = "直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime livingTime;

    /**
     * 真实时间
     */
    @Schema(name = "scheduleTime", description = "时间")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime scheduleTime;

    /**
     * 直播封面
     */
    @Schema(name = "cover", description = "直播封面")
    private String cover;

    /**
     * 直播封面
     */
    @Schema(name = "logo", description = "直播LOGO")
    private String logo;

    /**
     * 直播介绍
     */
    @Schema(name = "remark", description = "直播介绍")
    private String remark;

    /**
     * 直播方式
     */
    @Schema(name = "videoType", description = "直播方式: 1 真人直播, 2 视频直播(伪直播)")
    private Integer videoType;

    /**
     * 直播类型
     */
    @Schema(name = "livingType", description = "直播类型: 1 标准延迟(标准直播), 2 超低延迟(快直播)")
    private Integer livingType;

    /**
     * 清晰度
     */
    @Schema(name = "videoQuality", description = "清晰度: 1 流畅270p 2 标清480p 3 准高清720p 4 高清1080p 5 超高清2K")
    private Integer videoQuality;

    /**
     * 伪直播关联媒体业务ID
     */
    @Schema(name = "mediaBizId", description = "伪直播关联媒体业务ID")
    private Long mediaBizId;

    /**
     * 伪直播关联媒体业务播放次数, 默认值 1
     */
    @Schema(name = "cycleTimes", description = "伪直播关联媒体业务播放次数, 默认值 1")
    private Integer cycleTimes;


    /**
     * 直播状态 0 直播中  1 未直播
     */
    @Schema(name = "status", description = "直播状态 0 直播中  1 未直播")
    private Integer status;

}
