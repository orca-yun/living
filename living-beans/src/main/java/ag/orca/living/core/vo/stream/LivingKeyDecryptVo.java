package ag.orca.living.core.vo.stream;

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
public class LivingKeyDecryptVo extends BaseBean {

    @Schema(name = "roomId", description = "房间ID")
    private Long roomId;

    @Schema(name = "orgId", description = "机构ID")
    private Long orgId;

    @Schema(name = "sharePwd", description = "观看端密码")
    private String sharePwd;

    @Schema(name = "channelId", description = "渠道端ID")
    private Long channelId;


    public static LivingKeyDecryptVo build(String[] args) {
        return LivingKeyDecryptVo.builder()
                .orgId(Long.parseLong(args[0]))
                .roomId(Long.parseLong(args[1]))
                .sharePwd(args[2])
                .channelId(Long.parseLong(args[3]))
                .build();
    }
}