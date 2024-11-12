package ag.orca.living.core.entity.org;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class OrgRobot extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 机器人编号
     */
    private String code;

    /**
     * 机器人昵称
     */
    private String nickname;

    /**
     * 机器人头像
     */
    private String headIco;

}
