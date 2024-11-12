package ag.orca.living.core.ro.media;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "LivingMediaLibRo", description = "媒体库请求RO")
public class LivingMediaLibRo extends BaseBean {


    @Schema(name = "fileId", description = "文件ID")
    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @Schema(name = "name", description = "视频名称")
    @NotBlank(message = "视频名称不能为空")
    private String name;

    /**
     * bucket名称
     */
    @Schema(name = "bucket", description = "bucket名称")
    @NotBlank(message = "bucket名称不能为空")
    private String bucket;

    /**
     * 路径
     */
    @Schema(name = "pathName", description = "路径")
    @NotBlank(message = "路径不能为空")
    private String pathName;
}
