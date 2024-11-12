package ag.orca.living.core.vo.filepart;

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
public class FilePartInitResultVo extends BaseBean {


    @Schema(name = "fileId", description = "上传文件ID")
    private String fileId;

    @Schema(name = "fastStatus", description = "秒传状态 0 非 1 是")
    private Integer fastStatus;

    @Schema(name = "bucket", description = "存储桶")
    private String bucket;

    @Schema(name = "key", description = "关键KEY")
    private String key;
}
