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
public class UserBlacklistEvent extends BaseBean {

    private Long roomId;

    private String uid;

    private Boolean join;

}
