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
public class LivingRoomPage extends BaseEntity {

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
     * 是否有白板 0 关闭 1 开启
     */
    private Integer showDraw;


    /**
     * PPT是否展示 0 关闭 1 开启
     */
    private Integer showPpt;


    /**
     * 电脑端观看开关 0 关闭 1 开启
     */
    private Integer pcSwitch;

    /**
     * 移动端模板 二分屏、三分屏、全屏
     */
    private String mobileLayout;

    /**
     * PAD(PC)端模板
     */
    private String padPcLayout;


    /**
     * 直播封面背景图
     */
    private String bgImage;

    /**
     * 视频水印内容
     */
    private String watermark;


}
