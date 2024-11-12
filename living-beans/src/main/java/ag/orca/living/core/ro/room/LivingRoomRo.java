package ag.orca.living.core.ro.room;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "LivingRoomRo", description = "房间RO")
public class LivingRoomRo extends BaseBean {

    /**
     * ID
     */
    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    /**
     * 直播房间名称
     */
    @Schema(name = "name", description = "直播房间名称")
    @NotBlank(message = "直播名称不能为空")
    @Length(min = 1, max = 30, message = "直播名称最大30个字")
    private String name;


    /**
     * 直播时间
     */
    @Schema(name = "livingTime", description = "直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime livingTime;


    /**
     * 直播封面
     */
    @Schema(name = "cover", title = "直播封面")
    private String cover;

    /**
     * 直播LOGO
     */
    @Schema(name = "logo", title = "直播LOGO")
    private String logo;


    /**
     * 直播介绍
     */
    @Schema(name = "remark", title = "直播介绍")
    private String remark;


    /**
     * 直播类型
     */
    @Schema(name = "livingType", title = "直播类型: 1 标准延迟(标准直播), 2 超低延迟(快直播)")
    @NotNull(message = "直播类型不能为空")
    private Integer livingType;

    /**
     * 直播方式
     */
    @Schema(name = "videoType", description = "直播方式: 1 真人直播, 2 视频直播(伪直播)")
    @NotNull(message = "直播方式不能为空")
    private Integer videoType;


    /**
     * 伪直播关联媒体业务ID
     */
    @Schema(name = "mediaBizId", description = "伪直播关联媒体业务ID")
    private Long mediaBizId;


    /**
     * 伪直播关联媒体业务播放次数, 默认值1
     */
    @Schema(name = "cycleTimes", description = "伪直播关联媒体业务播放次数, 默认值1")
    private Integer cycleTimes;


}
