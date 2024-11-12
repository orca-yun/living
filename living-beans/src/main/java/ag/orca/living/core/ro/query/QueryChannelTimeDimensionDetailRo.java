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
@Schema(name = "QueryChannelTimeDimensionDetailRo", description = "渠道时间维度查询RO")
public class QueryChannelTimeDimensionDetailRo extends PageRo {

    /**
     * 时间
     */
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Schema(name = "date", description = "查询日期")
    private LocalDate date;

    /**
     * 渠道channelId
     */
    @Schema(name = "channelId", description = "渠道channelId")
    private Long channelId;

}
