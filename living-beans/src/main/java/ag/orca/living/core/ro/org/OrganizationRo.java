package ag.orca.living.core.ro.org;


import ag.orca.living.core.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "OrganizationRo", description = "机构请求RO")
public class OrganizationRo extends BaseBean {


    /**
     * 企业(机构)名称
     */
    @Schema(name = "name", description = "企业(机构)名称")
    @NotBlank(message = "企业名称不能为空")
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

    /**
     * 企业(机构)管理员账号
     */
    @Schema(name = "account", description = "企业(机构)管理员账号")
    @NotBlank(message = "企业(机构)管理员账号不能为空")
    private String account;

    /**
     * 企业(机构)管理员密码
     */
    @Schema(name = "pwd", description = "企业(机构)管理员密码")
    @NotBlank(message = "企业(机构)管理员密码不能为空")
    private String pwd;

    /**
     * 企业直播单价
     */
    @Schema(name = "livingPrice", description = "企业直播单价")
    @NotBlank(message = "企业直播单价不能为空")
    @Min(value = 1, message = "企业直播单价不能低于0.01元")
    private Long livingPrice;

    /**
     * 企业点播单价
     */
    @Schema(name = "videoPrice", description = "企业点播单价")
    @NotBlank(message = "企业点播单价不能为空")
    @Min(value = 1, message = "企业点播单价不能低于0.01元")
    private Long videoPrice;

    /**
     * 企业存储价格
     */
    @Schema(name = "storagePrice", description = "企业存储单价")
    @NotNull(message = "企业存储单价不能为空")
    @Min(value = 1, message = "企业存储单价不能低于0.01元")
    private Long storagePrice;


}
