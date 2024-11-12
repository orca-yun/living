package ag.orca.living.core.vo.stats;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class ChannelUserStaticsVo extends ChannelStaticsVo {

    /**
     * 渠道引流人数
     */
    private Integer cnt;
}
