package ag.orca.living.core.ro.filepart;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "RunFilePartMultipartRo", description = "初始化分片上传")
public class RunFilePartMultipartRo extends BaseBean {

    @Schema(name = "fileId", description = "上传文件ID")
    @NotBlank(message = "上传文件ID不能为空")
    private String fileId;

    @Schema(name = "partNum", description = "分片文件索引 从0开始")
    @NotNull(message = "分片文件索引值不能为空")
    private Integer partNum;

    @Schema(name = "file", description = "分片文件")
    @NotNull(message = "分片文件不能为空")
    private MultipartFile file;
}
