package ag.orca.living.core.vo.room;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomShareVo extends BaseBean {


    @Schema(name = "id", description = "id")
    private Long id;


    /**
     * 直播房间名称
     */
    @Schema(name = "name", description = "直播房间名称")
    private String name;


    /**
     * 直播时间
     */
    @Schema(name = "livingTime", description = "直播时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime livingTime;

    /**
     * 真实时间
     */
    @Schema(name = "scheduleTime", description = "时间")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime scheduleTime;

    /**
     * 直播封面
     */
    @Schema(name = "cover", description = "直播封面")
    private String cover;


    @Schema(name = "cover", description = "直播LOGO")
    private String logo;

    /**
     * 直播介绍
     */
    @Schema(name = "remark", description = "直播介绍")
    private String remark;

    /**
     * 直播类型
     */
    @Schema(name = "livingType", description = "直播类型: 1 标准延迟(标准直播), 2 超低延迟(快直播)")
    private Integer livingType;

    /**
     * 直播方式
     */
    @Schema(name = "videoType", description = "直播方式: 1 真人直播, 2 视频直播(伪直播)")
    private Integer videoType;

    /**
     * 清晰度
     */
    @Schema(name = "videoQuality", description = "清晰度: 1 流畅270p 2 标清480p 3 准高清720p 4 高清1080p 5 超高清2K")
    private Integer videoQuality;


    /**
     * 直播状态 0 直播中  1 未直播
     */
    @Schema(name = "status", description = "直播状态 0 直播中  1 未直播")
    private Integer status;


    @Schema(name = "interact", description = "互动设置")
    private InteractShareVo interact;

    @Schema(name = "page", description = "页面设置")
    private PageShareVo page;

    @Schema(name = "permission", description = "权限设置")
    private PermissionShareVo permission;

    @Schema(name = "goods", description = "商品设置")
    private GoodsShareVo goods;

    @Schema(name = "coursewares", description = "课件列表")
    private List<LivingCoursewareVo> coursewares;

    @Schema(name = "giftItems", description = "礼物列表")
    private List<LivingRoomMarketGiftItemVo> giftItems;

    @Schema(name = "coursewares", description = "商品列表")
    private List<LivingRoomMarketGoodsItemVo> goodsItems;


    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Data
    @SuperBuilder
    @Schema(name = "InteractShareVo", description = "房间互动配置VO")
    public static class InteractShareVo extends BaseBean {

        /**
         * 人数倍数开关 0 关闭 1 开启
         */
        @Schema(name = "tupleEnable", description = "人数倍数开关 0 关闭 1 开启")
        private Integer tupleEnable;

        /**
         * 人数倍数
         */
        @Schema(name = "tupleNum", description = "人数倍数")
        private Integer tupleNum;

    }


    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Data
    @SuperBuilder
    @Schema(name = "PageShareVo", description = "房间页配置VO")
    public static class PageShareVo extends BaseBean {

        /**
         * 白板开关 0 关闭 1 开启
         */
        @Schema(name = "showDraw", description = "白板开关 0 关闭 1 开启")
        private Integer showDraw;

        /**
         * 课件展示开关 0 关闭 1 开启
         */
        @Schema(name = "showPpt", description = "课件展示开关 0 关闭 1 开启")
        private Integer showPpt;

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

    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Data
    @SuperBuilder
    @Schema(name = "PermissionShareVo", description = "房间权限配置VO")
    public static class PermissionShareVo extends BaseBean {
        /**
         * 观看权限
         */
        @Schema(name = "permissionType", description = "观看权限: 1 无密码 2 密码 3 付费")
        private Integer permissionType;

        /**
         * 权限JSON
         */
        @Schema(name = "permissionJson", description = "权限配置(字符串) 权限为密码(填入密码文本), 付费(填入金额 分)")
        private String permissionJson;
    }


    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @Data
    @SuperBuilder
    @Schema(name = "GoodsShareVo", description = "房间商品配置VO")
    public static class GoodsShareVo extends BaseBean {
        /**
         * 购买按钮描述
         */
        @Schema(name = "btnTxt", description = "购买按钮描述")
        private String btnTxt;

        /**
         * 倒计时(秒)
         */
        @Schema(name = "countDown", description = "倒计时(秒): 0 不设置, 60 1分钟, 120 2分钟, 300 5分钟, 600 10分钟")
        private Integer countDown;

        /**
         * 展示样式
         */
        @Schema(name = "showStyle", description = "展示样式: 1 小弹框 2 大弹框")
        private Integer showStyle;
    }


}
