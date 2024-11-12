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
public class LivingStreamPushAddressVo extends BaseBean {

    @Schema(name = "rtmp", description = "rtmp")
    private String rtmp;

    @Schema(name = "whipServer", description = "whip服务器")
    private String whipServer;

    @Schema(name = "webrtc", description = "webrtc")
    private String webrtc;

    @Schema(name = "srt", description = "srt")
    private String srt;

    @Schema(name = "rtmpOverSrt", description = "rtmpOverSrt")
    private String rtmpOverSrt;

    @Schema(name = "obsServer", description = "OBS服务器")
    private String obsServer;

    @Schema(name = "obsStreamCode", description = "OBS推流码")
    private String obsStreamCode;

}
