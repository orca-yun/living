package ag.orca.living.core.entity.control;

import ag.orca.living.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 氛围场控话术模板
 *
 * @TableName t_atmosphere_template_control
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlAtmosphereTemplate extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 礼物内容
     */
    private String giftContent;


}