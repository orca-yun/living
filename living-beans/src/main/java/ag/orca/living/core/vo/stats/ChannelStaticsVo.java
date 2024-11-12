package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ChannelStaticsVo extends BaseBean {

    /**
     * 渠道 ID
     */
    private Long channelId;

    /**
     * 房间 ID
     */
    private Long roomId;
}
