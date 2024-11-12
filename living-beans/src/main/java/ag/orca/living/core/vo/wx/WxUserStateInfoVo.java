package ag.orca.living.core.vo.wx;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class WxUserStateInfoVo extends BaseBean {

    private Long orgId;

    private Long roomId;

    private Long channelId;

    private Long random;

    private Integer platform;
}
