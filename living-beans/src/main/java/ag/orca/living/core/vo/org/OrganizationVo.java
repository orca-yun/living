package ag.orca.living.core.vo.org;

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
public class OrganizationVo extends BaseBean {

    @Schema(name = "id", description = "机构ID")
    private Long id;

    /**
     * 企业(机构)名称
     */
    @Schema(name = "name", description = "企业(机构)名称")
    private String name;

    /**
     * 企业唯一标识
     */
    @Schema(name = "orgCode", description = "企业唯一标识")
    private String orgCode;

    /**
     * LOGO
     */
    @Schema(name = "orgLogo", description = "LOGO")
    private String orgLogo;


    /**
     * 房间背景设置
     */
    @Schema(name = "orgRoomBg", description = "房间背景设置")
    private String orgRoomBg;

    /**
     * 直播间公告
     */
    @Schema(name = "notice", description = "直播间公告")
    private String notice;

}
