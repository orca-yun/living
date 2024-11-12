package ag.orca.living.core.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class VideInfoGenAckEvent extends VideoInfoGenEvent {

    private String key;

    private Long duration;

    private Long capacity;
}
