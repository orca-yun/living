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
public class LivingStreamTransPullAddressVo extends BaseBean {

    @Schema(name = "rtmp", description = "rtmp")
    private String rtmp;

    @Schema(name = "flv", description = "flv")
    private String flv;

    @Schema(name = "m3u8", description = "m3u8")
    private String m3u8;

    @Schema(name = "webrtc", description = "webrtc")
    private String webrtc;
}
