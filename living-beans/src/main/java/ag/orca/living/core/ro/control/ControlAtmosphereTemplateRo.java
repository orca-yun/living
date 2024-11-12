package ag.orca.living.core.ro.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "氛围场控模板 Ro")
public class ControlAtmosphereTemplateRo extends BaseBean {

    @Schema(title = "模板ID")
    private Long id;

    /**
     * 直播间ID
     */
    @Schema(title = "房间ID")
    @NotNull(message = "房间号不能为空")
    private Long roomId;

    /**
     * 文本内容列表
     */
    @Schema(title = "文本内容列表")
    private List<String> textContentList;

    /**
     * 礼物ID列表
     */
    @Schema(title = "礼物ID列表")
    private List<Long> giftIdList;
}
