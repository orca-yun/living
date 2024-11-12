package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class VideoStartLiveEvent extends BaseBean {

    /**
     * 视频触发的推流记录操作ID
     */
    private String oplogId;

    private Integer liveSource;

    private Long roomId;

    private String rtmp;

    private String fileName;

    private String bucket;

    private String pathName;

    /**
     * 媒体类型 类型: 1 手动上传 2 从录制而来
     */
    private Integer mediaType;

    /**
     * 房间ID
     */
    private Long mediaRoomId;

    /**
     * 记录ID
     */
    private Long mediaRoomRecordId;

    /**
     * 次数
     */
    private Integer times;

    /**
     * 目标时长
     */
    private Long targetDuration;
}
