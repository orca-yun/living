package ag.orca.living.core.vo.stream;

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
public class LivingE2eAddressVo extends BaseBean {

    @Schema(name = "adminUrl", description = "管理端地址")
    private String adminUrl;

    @Schema(name = "anchorUrl", description = "主播端地址")
    private String anchorUrl;

    @Schema(name = "assistantUrl", description = "助理端登陆地址")
    private String assistantUrl;

    @Schema(name = "shareUrl", description = "观看端地址[通过短链会自动转发]")
    private String shareUrl;

    @Schema(name = "playUrl", description = "观看端回放地址[通过短链会自动转发]")
    private String playUrl;

    @Schema(name = "shortUrl", description = "观看端短地址")
    private String shortUrl;
}
