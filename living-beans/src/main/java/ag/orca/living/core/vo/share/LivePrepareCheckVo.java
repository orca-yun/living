package ag.orca.living.core.vo.share;

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
public class LivePrepareCheckVo extends BaseBean {

    @Schema(name = "result", description = " 1 通过  2 token过期 3 token 非法 4 黑名单中")
    private Integer result;


}
