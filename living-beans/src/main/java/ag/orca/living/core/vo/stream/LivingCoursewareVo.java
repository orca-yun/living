package ag.orca.living.core.vo.stream;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingCoursewareVo extends BaseBean {
    @Schema(name = "id", description = "课件ID")
    private Long id;

    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    @Schema(name = "roomId", description = "房间ID")
    private Long roomId;

    @Schema(name = "name", description = "课件名称")
    private String name;

    @Schema(name = "status", description = "状态 1 转换中 2 转换成功 3 转换失败")
    private Integer status;

    @Schema(name = "images", description = "课件图片列表")
    private List<String> images;


}
