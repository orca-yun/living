package ag.orca.living.core.ro.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发送氛围消息入参
 *
 * 
 * @date 2024-05-02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "发送氛围消息入参")
public class SendAtmosphereMessageRo extends BaseBean {

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

    /**
     * 发送数量
     */
    @Schema(title = "发送数量")
    @NotNull(message = "发送数量不能为空")
    private Integer quantity;

    /**
     * 文本消息发送间隔, 单位: 秒
     */
    @Schema(title = "文本消息发送间隔", description = "单位: 秒")
    private BigDecimal textInterval;

    /**
     * 礼物消息发送间隔, 单位: 秒
     */
    @Schema(title = "礼物消息发送间隔", description = "单位: 秒")
    private BigDecimal giftInterval;
}
