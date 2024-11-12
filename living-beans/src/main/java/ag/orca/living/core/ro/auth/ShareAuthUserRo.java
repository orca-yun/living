package ag.orca.living.core.ro.auth;

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
@Schema(name = "ShareAuthUserRo", description = "授权请求RO")
public class ShareAuthUserRo extends BaseBean {

    @Schema(name = "nickname", description = "昵称")
    private String nickname;

    @Schema(name = "headIco", description = "头像[先为空]")
    private String headIco;
}
