package ag.orca.living.core.ro.media;

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
@Schema(name = "LivingMediaLibEditRo", description = "媒体库请求RO")
public class LivingMediaLibEditRo extends BaseBean {

    @Schema(name = "orgId", description = "媒体库文件ID")
    @NotNull(message = "媒体库文件ID不能为空")
    private Long id;


    @Schema(name = "name", description = "视频名称")
    @NotBlank(message = "视频名称不能为空")
    private String name;

}
