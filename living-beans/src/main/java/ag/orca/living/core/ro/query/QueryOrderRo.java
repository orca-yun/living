package ag.orca.living.core.ro.query;

import ag.orca.living.core.ro.PageRo;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "QueryOrderRo", description = "订单分页查询")
public class QueryOrderRo extends PageRo {
    /**
     * 订单发生的直播间id
     */
    @Schema(name = "roomId", description = "订单发生的直播间id")
    private Long roomId;


    /**
     * 订单来源渠道id，目前是6位数字
     */
    @Schema(name = "channelIds", description = "订单来源渠道id，目前是6位数字")
    private List<Long> channelIds;


    /**
     * 订单状态 1待支付 2已支付 3已取消
     */
    @Schema(title = "订单状态 1待支付 2已支付 3已取消 4超时未支付")
    private Integer orderStatus;


    @Schema(title = "订单日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;


}
