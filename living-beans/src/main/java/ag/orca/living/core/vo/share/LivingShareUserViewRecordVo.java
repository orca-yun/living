package ag.orca.living.core.vo.share;

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
public class LivingShareUserViewRecordVo extends BaseBean {

    /**
     * 观看日期
     */
    @Schema(name = "viewDate", description = "观看日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate viewDate;

    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "房间号")
    private Long roomId;

    /**
     * 渠道ID
     */
    @Schema(name = "channelId", description = "渠道ID")
    private Long channelId;


    /**
     * 渠道ID
     */
    @Schema(name = "channelName", description = "渠道名称")
    private String channelName;
    /**
     * 用户UID
     */
    @Schema(name = "uid", description = "用户ID")
    private String uid;

    /**
     * 用户昵称
     */
    @Schema(name = "nickname", description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(name = "headIco", description = "用户头像")
    private String headIco;

    /**
     * 用户观看时长
     */
    @Schema(name = "viewDuration", description = "用户观看时长 秒")
    private Long viewDuration;

    /**
     * 礼物数量
     */
    @Schema(name = "giftNum", description = "送礼数")
    private Long giftNum;

    /**
     * 消息数量
     */
    @Schema(name = "msgNum", description = "消息数")
    private Long msgNum;

    /**
     * UA
     */
    @Schema(name = "userAgent", description = "UA")
    private String userAgent;

    /**
     * 上线时间戳
     */
    @Schema(name = "tsOnline", description = "上线时间戳")
    private Long tsOnline;

    /**
     * 下线时间戳
     */
    @Schema(name = "tsOffline", description = "下线时间戳")
    private Long tsOffline;


    /**
     * 上线时刻
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "onlineTime", description = "上线时刻")
    private LocalDateTime onlineTime;

    /**
     * 下线时刻
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "offlineTime", description = "下线时刻")
    private LocalDateTime offlineTime;

    /**
     * 状态 1 在线 2 离线
     */
    @Schema(name = "status", description = "状态 1 在线 2 离线")
    private Integer status;

    /**
     * 上线次数
     */
    @Schema(name = "onlineTimes", description = "上线次数")
    private Integer onlineTimes;

}
