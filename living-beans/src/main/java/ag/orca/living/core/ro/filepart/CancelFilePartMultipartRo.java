package ag.orca.living.core.ro.filepart;

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
@Schema(name = "CancelFilePartMultipartRo", description = "放弃文件上传")
public class CancelFilePartMultipartRo extends BaseBean {

    @Schema(name = "fileId", description = "上传文件ID")
    @NotBlank(message = "上传文件ID不能为空")
    private String fileId;


}
