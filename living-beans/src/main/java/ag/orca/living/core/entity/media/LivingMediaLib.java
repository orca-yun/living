package ag.orca.living.core.entity.media;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class LivingMediaLib extends BaseEntity {

    private Long id;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 媒体名称
     */
    private String name;

    /**
     * 文件原名称
     */
    private String fileName;

    /**
     * bucket名称
     */
    private String bucket;

    /**
     * 路径
     */
    private String pathName;


    /**
     * 时长
     */
    private Long duration;

    /**
     * 视频大小
     */
    private Long capacity;

    /**
     * 截取图
     */
    private String image;

    /**
     * 媒体类型 类型: 1 手动上传 2 从录制而来
     */
    private Integer mediaType;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 记录ID
     */
    private Long roomRecordId;


    /**
     * 转码状态
     */
    private Integer convertStatus;

}
