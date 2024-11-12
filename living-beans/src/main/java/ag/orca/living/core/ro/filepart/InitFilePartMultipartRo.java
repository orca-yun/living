package ag.orca.living.core.ro.filepart;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "InitFilePartMultipartRo", description = "初始化分片上传")
public class InitFilePartMultipartRo extends BaseBean {

    @Schema(name = "fileMd5", description = "文件md5, 秒传")
    private String fileMd5;

    @Schema(name = "fileCrc32", description = "文件crc32, 秒传")
    private Long fileCrc32;

    @Schema(name = "fileName", description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @Schema(name = "fileSize", description = "文件大小")
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    @Schema(name = "filePart", description = "分片大小")
    @NotNull(message = "分片大小不能为空")
    private Long filePart;

}
