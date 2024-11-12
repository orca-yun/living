package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryAccountRecordRo", description = "账户记录分页查询")
public class QueryAccountRecordRo extends PageRo {

    /**
     * 记录类型
     */
    @Schema(name = "recordType", description = "记录类型 1 充值 2 扣费")
    private Integer recordType;
}
