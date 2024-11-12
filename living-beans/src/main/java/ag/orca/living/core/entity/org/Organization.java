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
public class Organization extends BaseEntity {

    private Long id;

    /**
     * 企业(机构)名称
     */
    private String name;

    /**
     * 企业唯一标识
     */
    private String orgCode;

    /**
     * LOGO
     */
    private String orgLogo;


    /**
     * 房间背景设置
     */
    private String orgRoomBg;


    /**
     * 直播间公告
     */
    private String notice;


    /**
     * 企业直播单价
     */
    private Long livingPrice;

    /**
     * 企业点播单价
     */
    private Long videoPrice;

    /**
     * 企业存储价格
     */
    private Long storagePrice;

}
