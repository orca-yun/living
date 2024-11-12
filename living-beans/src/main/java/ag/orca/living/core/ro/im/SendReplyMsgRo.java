package ag.orca.living.core.ro.im;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.MsgTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "SendReplyMsgRo", description = "发送(回复)消息RO")
public class SendReplyMsgRo extends BaseBean {

    @Schema(name = "data", description = "被回复的消息内容")
    private String data;

    @Schema(name = "replyData", description = "回复的内容")
    private String replyData;

    @Builder.Default
    @Schema(name = "msgType", description = "消息类型,默认normal img reply cancel")
    private MsgTypeEnum msgType = MsgTypeEnum.normal;

}
