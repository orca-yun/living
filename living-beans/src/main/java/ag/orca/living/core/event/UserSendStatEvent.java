package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSendStatEvent extends BaseBean {

    private LocalDate viewDate;

    private Long orgId;

    private Long roomId;

    private Long channelId;

    private String uid;

    /**
     * 1 发言 2 送礼物 3 ...
     */
    private Integer type;
}
