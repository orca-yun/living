package ag.orca.living.core.vo.channel;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 渠道名称和分佣比例
 *
 * 
 * @date 2024-05-7
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "渠道名称和分佣比例")
public class ChannelNameAndRatioVo extends BaseBean {

    /**
     * 分佣比例
     */
    @Schema(title = "分佣比例")
    private List<Integer> ratios;

    /**
     * 渠道名称
     */
    @Schema(title = "渠道名称")
    private List<String> channelNames;


}
