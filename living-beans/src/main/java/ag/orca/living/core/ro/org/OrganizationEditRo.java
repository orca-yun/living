package ag.orca.living.core.ro.org;

import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 企业信息编辑RO
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "OrganizationEditRo", description = "机构编辑RO")
public class OrganizationEditRo extends BaseBean {


    @Schema(name = "id", description = "机构ID")
    @NotNull(message = "企业(机构)ID不能为空")
    private Long id;

    /**
     * 企业(机构)名称
     */
    @Schema(name = "name", description = "企业(机构)名称")
    @NotBlank(message = "企业(机构)名称不能为空")
    private String name;

    /**
     * 企业(机构)LOGO
     */
    @Schema(name = "orgLogo", description = "企业(机构)LOGO")
    @NotBlank(message = "企业(机构)LOGO不能为空")
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
