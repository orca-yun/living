package ag.orca.living.core.vo.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 氛围场控VO
 *
 * 
 * @date 2024-05-02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "氛围场控VO")
public class ControlAtmosphereFieldVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "氛围场控ID")
    private Long id;

    /**
     * 房间ID
     */
    @Schema(title = "房间ID")
    private Long roomId;


    @Schema(title = "礼物内容")
    private List<Long> giftIdList;

    @Schema(title = "文本内容")
    private List<String> textContentList;

    /**
     * 发送数量
     */
    @Schema(title = "发送数量")
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

    /**
     * 执行状态, 0: 已停止, 1: 已启动
     */
    @Schema(title = "执行状态", description = "0: 已停止, 1: 已启动; 暂时根据 expEndTime 判断消息是否均已发完")
    private Integer execStatus;

    /**
     * 预计结束时间
     */
    @Schema(title = "预计结束时间", description = "此时间为最后一条消息预计发送完成的时间，可用于判断本次场控是否终止")
    private LocalDateTime expEndTime;

}
