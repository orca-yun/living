package ag.orca.living.core.entity.sys;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class SysGift extends BaseEntity {

    private Long id;

    /**
     * 礼物名称
     */
    private String name;

    /**
     * 礼物图片
     */
    private String img;

    /**
     * 礼物价格,分
     */
    private Long price;

}
