package ag.orca.living.core.vo.control;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 剧本VO
 *
 * 
 * @date 2024-04-14
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "剧本VO")
public class ControlScriptVo extends BaseBean {

    /**
     * ID
     */
    @Schema(title = "剧本ID")
    private Long id;

    /**
     * 机器人编号
     */
    @Schema(title = "机器人编号")
    private String robotCode;

    /**
     * 机器人昵称
     */
    @Schema(title = "机器人昵称")
    private String robotNickname;

    /**
     * 机器人头像
     */
    @Schema(title = "机器人头像", description = "URL形式")
    private String robotHeadIco;

    /**
     * 消息类别, 1: 文本, 2: 礼物
     */
    @Schema(title = "消息类别", description = "1: 文本, 2: 礼物")
    private Integer messageType;

    /**
     * 发送内容
     */
    @Schema(title = "发送内容")
    private String content;
}
