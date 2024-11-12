package ag.orca.living.core.vo.wx;

import ag.orca.living.core.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class WxUserInfoVo extends BaseBean {


    private String id;

    /**
     * 当前开发者账号唯一
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;


    /**
     * 用户头像URL地址
     */
    private String headImageUrl;

    /**
     * 用户的unionId,唯一
     */
    private String unionId;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;


}
