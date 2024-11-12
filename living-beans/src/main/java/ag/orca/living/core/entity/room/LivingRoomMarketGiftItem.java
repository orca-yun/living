package ag.orca.living.core.entity.room;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingRoomMarketGiftItem extends BaseEntity {

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
     * 礼品库ID
     */
    private Long giftLibId;

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


    /**
     * 礼物类型 1 静态礼物 2 动态礼物
     */
    private Integer giftType;

    /**
     * 状态 1 上架 2 下架
     */
    private Integer status;

    /**
     * 排序优先级
     */
    private Integer priority;

}
