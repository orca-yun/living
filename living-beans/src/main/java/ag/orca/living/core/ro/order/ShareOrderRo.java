package ag.orca.living.core.ro.order;

import ag.orca.living.core.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "ShareOrderRo", description = "查询订单Ro")
public class ShareOrderRo extends BaseBean {
    @Schema(name = "roomId", description = "订单发生的直播间id")
    @NotNull(message = "直播房间ID不能为空")
    private Long roomId;

    @Schema(name = "userId", description = "下单人ID")
    @NotNull(message = "下单人ID不能为空")
    private Long userId;

    @Schema(title = "订单日期")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
}
