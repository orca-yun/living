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
public class RollbackMsgEvent extends BaseBean {

    private Long roomId;

    private String msgUid;

    private String senderName;

    private String senderHeadIco;

    private String senderUid;

    private Integer senderType;

    private Long ts;
}
