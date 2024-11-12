package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LiveStatusNotifyEvent extends BaseBean {

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间记录ID
     */


    private Long roomRecordId;

    /**
     * 直播状态
     */
    private LiveRecordStatusEnum status;

}
