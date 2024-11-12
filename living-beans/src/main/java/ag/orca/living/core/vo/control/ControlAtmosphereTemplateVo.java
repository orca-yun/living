package ag.orca.living.core.vo.control;

import ag.orca.living.core.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 氛围场控模板
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControlAtmosphereTemplateVo extends BaseBean {

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
    private List<String> textContent;

    /**
     * 礼物内容
     */
    private List<Long> giftContent;


}