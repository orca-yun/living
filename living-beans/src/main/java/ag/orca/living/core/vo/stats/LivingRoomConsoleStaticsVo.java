package ag.orca.living.core.vo.stats;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.overview.LivingRoomStaticsRecordVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class LivingRoomConsoleStaticsVo extends BaseBean {


    @Schema(name = "pv", description = "总观看人次(PV)")
    @Builder.Default
    private Long pv = 0L;

    @Schema(name = "uv", description = "总观看人次(UV)")
    @Builder.Default
    private Long uv = 0L;

    @Schema(name = "onlineNum", description = "在线人数")
    @Builder.Default
    private Long onlineNum = 0L;

    @Schema(name = "records", description = "记录统计明细")
    @Builder.Default
    private List<LivingRoomStaticsRecordVo> records = new ArrayList<>();


}
