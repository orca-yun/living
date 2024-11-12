package ag.orca.living.core.entity.stream;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingCourseware extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 直播房间ID
     */
    private Long roomId;

    /**
     * 课件名称
     */
    private String name;


    /**
     * 状态 1 转换中 2 转换成功 3 转换失败
     */
    private Integer status;

}
