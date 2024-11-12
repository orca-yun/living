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
public class VideoInfoGenEvent extends BaseBean {

    private String bucket;

    private String pathName;

    private Long mediaId;

    private Integer start;

    // 1 PNG 2 GIF
    private Integer type;

}
