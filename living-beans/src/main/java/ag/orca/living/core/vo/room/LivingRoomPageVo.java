package ag.orca.living.core.vo.room;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomPageVo extends BaseBean {

    @Schema(name = "id", description = "id")
    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 直播房间ID
     */
    @Schema(name = "roomId", description = "直播房间ID")
    private Long roomId;


    /**
     * 课件展示开关 0 关闭 1 开启
     */
    @Schema(name = "showPpt", description = "课件展示开关 0 关闭 1 开启")
    private Integer showPpt;

    /**
     * 白板开关 0 关闭 1 开启
     */
    @Schema(name = "showDraw", description = "白板开关 0 关闭 1 开启")
    private Integer showDraw;

    /**
     * 电脑端观看开关 0 关闭 1 开启
     */
    @Schema(name = "pcSwitch", description = "电脑端观看开关 0 关闭 1 开启")
    private Integer pcSwitch;

    /**
     * 移动端模板 1:全屏 2:二分屏 3:三分屏
     */
    @Schema(name = "mobileLayout", description = "移动端模板 1:全屏 2:二分屏 3:三分屏")
    private String mobileLayout;

    /**
     * PAD(PC)端模板
     */
    @Schema(name = "padPcLayout", description = "PAD(PC)端模板")
    private String padPcLayout;

    /**
     * 直播封面背景图
     */
    @Schema(name = "bgImage", description = "直播封面背景图")
    private String bgImage;

    /**
     * 视频水印内容
     */
    @Schema(name = "watermark", description = "视频水印内容")
    private String watermark;
}

