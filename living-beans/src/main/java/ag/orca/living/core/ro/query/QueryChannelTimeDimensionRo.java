package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryChannelTimeDimensionRo", description = "渠道时间维度查询RO")
public class QueryChannelTimeDimensionRo extends PageRo {

    /**
     * 时间
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Schema(name = "date", description = "查询日期")
    private LocalDate date;

    /**
     * 引流人数排序字段 0正序 1倒叙
     */
    @Schema(name = "flow", description = "引流人数排序字段 0正序 1倒叙 和 amount最多只传一个")
    private Integer flow;

    /**
     * 成交金额 0正序 1倒叙
     */
    @Schema(name = "amount", description = "成交金额 0正序 1倒叙 和 flow最多只传一个")
    private Integer amount;


}
