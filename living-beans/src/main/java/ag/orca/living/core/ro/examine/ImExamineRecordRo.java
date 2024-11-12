package ag.orca.living.core.ro.examine;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.enums.ExamineStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "ImExamineRecordRo", description = "待审核记录RO")
public class ImExamineRecordRo extends BaseBean {

    @Schema(name = "id", description = "消息ID")
    @NotBlank(message = "消息ID不能为空")
    private String id;

    /**
     * 审批状态
     */
    @Schema(name = "examineStatus", description = "审批状态 init, approve, disapprove")
    @NotNull(message = "审批状态不能为空")
    private ExamineStatusEnum examineStatus;

    @Schema(name = "operateUid", description = "操作人、可为空、后端会补全")
    private String operateUid;

    @Schema(name = "operateNickname", description = "操作昵称、可为空、后端会补全")
    private String operateNickname;
}
