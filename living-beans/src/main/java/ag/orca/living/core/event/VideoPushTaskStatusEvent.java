package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class VideoPushTaskStatusEvent extends BaseBean {


    private Integer liveSource;

    //一定有
    private String oplogId;

    private Long roomId;

    private Long pushTaskId;

    private Integer status;

    private Long duration;

    private String msg;

}
