package ag.orca.living.core.entity.share;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingShareUserViewRecord extends BaseEntity {

    private Long id;

    /**
     * 观看日期
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate viewDate;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 直播记录ID[可以为空]
     */
    private Long roomRecordId;

    /**
     * 渠道ID
     */
    private Long channelId;

    /**
     * 用户UID
     */
    private String uid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String headIco;

    /**
     * 用户观看时长
     */
    private Long viewDuration;

    /**
     * 礼物数量
     */
    private Long giftNum;

    /**
     * 消息数量
     */
    private Long msgNum;


    /**
     * UA
     */
    private String userAgent;

    /**
     * 上线时间戳
     */
    private Long tsOnline;

    /**
     * 下线时间戳
     */
    private Long tsOffline;

    /**
     * 上线时刻
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime onlineTime;

    /**
     * 下线时刻
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime offlineTime;


    /**
     * 事件时间戳
     */
    private Long eventTs;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventTime;


    /**
     * 状态 1 在线 2 离线
     */
    private Integer status;

    /**
     * 上线次数
     */
    private Integer onlineTimes;

}
