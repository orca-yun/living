package ag.orca.living.core.ro.query;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryImExamineRecordRo", description = "待审核消息的RO")
public class QueryImExamineRecordRo extends BaseBean {

    @Schema(name = "size", description = "大小")
    private Integer size;

    @Schema(name = "keyword", description = "关键字")
    private String keyword;
}
