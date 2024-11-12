package ag.orca.living.core.entity.room;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingRoom extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间名称
     */
    private String name;

    /**
     * 主播密码
     */
    private String anchorPwd;

    /**
     * 助理密码
     */
    private String assistantPwd;

    /**
     * 用户端密码
     */
    private String sharePwd;

    /**
     * 直播时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime livingTime;

    /**
     * 真实时间
     */
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime scheduleTime;

    /**
     * 直播封面
     */
    private String cover;

    /**
     * 直播logo
     */
    private String logo;

    /**
     * 直播介绍
     */
    private String remark;

    /**
     * 直播类型: 1 标准延迟(标准直播), 2 超低延迟(快直播)
     */
    private Integer livingType;

    /**
     * 直播方式: 1 真人直播, 2 视频直播(伪直播)
     */
    private Integer videoType;

    /**
     * 清晰度: 1 流畅270p 2 标清480p 3 准高清720p 4 高清1080p 5 超高清2K
     */
    private Integer videoQuality;


    /**
     * 伪直播关联媒体业务ID
     */
    private Long mediaBizId;

    /**
     * 伪直播关联媒体循环次数, 默认值 1
     */
    private Integer cycleTimes;

    /**
     * 直播状态 0 直播中  1 未直播
     */
    private Integer status;
}
