package ag.orca.living.core.ro.im;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "RollbackMsgRo", description = "撤回消息RO")
public class RollbackMsgRo extends BaseBean {


    @Schema(name = "msgUid", description = "被撤回的消息UID")
    @NotBlank(message = "消息UID不能为空")
    private String msgUid;


}
