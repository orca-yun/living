package ag.orca.living.core.entity.share;

import ag.orca.living.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class UserChannelRelation extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 企业 ID
     */
    private Long orgId;

    /**
     * 渠道 ID
     */
    private Long channelId;

    /**
     * 房间 ID
     */
    private Long roomId;

    /**
     * 直播记录 ID
     */
    private Long liveRecordId;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * openId
     */
    private String openId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate bindDate;

}
