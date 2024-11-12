package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryRoomRo", description = "房间分页查询")
public class QueryRoomRo extends PageRo {

    @Schema(name = "name", description = "房间名称")
    private String name;

    @Schema(name = "videoType", description = "直播方式: 1 真人直播, 2 视频直播(伪直播)")
    private Integer videoType;

}
