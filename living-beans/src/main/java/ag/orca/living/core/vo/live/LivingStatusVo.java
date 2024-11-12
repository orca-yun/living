package ag.orca.living.core.vo.live;

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
public class LivingStatusVo extends BaseBean {

    private LiveRecordStatusEnum status;

    private Long recordId;
}
