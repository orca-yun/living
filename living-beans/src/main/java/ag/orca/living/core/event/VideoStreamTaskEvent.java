package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.LiveSourceEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class VideoStreamTaskEvent extends BaseBean {

    private Long roomId;

    private Long mediaId;

    private Integer times;

    private Long targetDuration;

    private LiveSourceEnum liveSource;

}
