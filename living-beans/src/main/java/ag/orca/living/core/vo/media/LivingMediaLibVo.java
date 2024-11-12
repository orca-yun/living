package ag.orca.living.core.vo.media;

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
public class LivingMediaLibVo extends BaseBean {

    private Long id;

    /**
     * 机构ID
     */
    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    /**
     * 媒体名称
     */
    @Schema(name = "name", description = "媒体名称")
    private String name;

    /**
     * 文件名称
     */
    @Schema(name = "fileName", description = "文件名称")
    private String fileName;

    /**
     * bucket名称
     */
    @Schema(name = "bucket", description = "bucket名称")
    private String bucket;

    /**
     * 路径
     */
    @Schema(name = "pathName", description = "路径")
    private String pathName;


    /**
     * 时长
     */
    @Schema(name = "duration", description = "时长(秒)")
    private Long duration;

    /**
     * 时长
     */
    @Schema(name = "capacity", description = "视频大小(字节)")
    private Long capacity;

    /**
     * 截取图
     */
    @Schema(name = "image", description = "截取图")
    private String image;

    /**
     * 媒体类型 类型: 1 手动上传 2 从录制而来
     */
    @Schema(name = "mediaType", description = "媒体类型 类型: 1 手动上传 2 从录制而来")
    private Integer mediaType;

    /**
     * 房间ID
     */
    @Schema(name = "roomId", description = "房间ID")
    private Long roomId;

    /**
     * 记录ID
     */
    @Schema(name = "roomRecordId", description = "记录ID")
    private Long roomRecordId;


    @Schema(name = "convertStatus", description = "转码状态 0 转码进行中 1 转码成功 2 转码失败")
    private Integer convertStatus;

}
