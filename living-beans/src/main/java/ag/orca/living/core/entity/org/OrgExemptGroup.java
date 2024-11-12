package ag.orca.living.core.entity.org;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgExemptGroup extends BaseBean {

    private Long id;

    private Long orgId;

    /**
     * 免审词名称
     */
    private String name;

    /**
     * 免审词列表
     */
    private List<String> words;
}
