package ag.orca.living.core.entity.sys;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class SysTlpay extends BaseEntity {
    private Long id;

    /**
     * 通联支付商户号
     */
    private String cusId;

    /**
     * 通联支付appid
     */
    private String appId;

    /**
     * 微信支付的appid
     */
    private String wxAppId;

    /**
     * 通联支付基础url
     */
    private String apiBaseUrl;

    /**
     * 通联支付签名方式 RSA、MD5、SM2
     */
    private String signType;


    /**
     * 版本
     */
    private String version;

    /**
     * 通联支付商户私钥 请求 加签
     */
    private String mchRsaPriKey;

    /**
     * 通联支付平台公钥 结果 验签
     */
    private String tlRsaPubKey;

    /**
     * 异步通知地址
     */
    private String notify;

    /**
     * 超时时间 默认 10
     */
    private int valid;
}
