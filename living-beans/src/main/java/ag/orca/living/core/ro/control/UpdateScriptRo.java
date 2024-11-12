package ag.orca.living.core.ro.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 编辑剧本入参
 *
 * 
 * @date 2024-04-14
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "编辑剧本入参")
public class UpdateScriptRo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "剧本ID")
    @NotNull(message = "剧本ID不能为空")
    private Long id;

    /**
     * 消息类别, 1: 文本, 2: 礼物
     */
    @Schema(title = "消息类别", description = "1: 文本, 2: 礼物")
    @NotNull(message = "消息类别不能为空")
    private Integer messageType;

    /**
     * 发送内容
     */
    @Schema(title = "发送内容", description = "如果消息类别为礼物，则传直播间礼物ID")
    @NotBlank(message = "发送内容不能为空")
    private String content;

}
