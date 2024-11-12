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
public class CoursewareConvertEvent extends BaseBean {

    private Long recordId;
    private Long roomId;
    private String fileName;
    private String bucket;
    private String key;
}
