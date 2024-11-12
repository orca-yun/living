package ag.orca.living.core.thirdparty.tlpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 通联统一支付参数
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
@Schema(name = "TlUnifiedPayParam", description = "通联返回基类")
public class TlUnifiedPayParam extends TlBaseParam {
    /**
     * 交易金额，单位为分
     */
    @Schema(name = "trxamt", description = "交易金额，单位为分")
    private Long trxamt;

    /**
     * 商户交易订单号
     */
    @Schema(name = "reqsn", description = "商户交易订单号不能为空")
    private String reqsn;

    /**
     * 交易方式
     */
    @Schema(name = "paytype", description = "交易方式不能为空")
    private String paytype;

    /**
     * 商户生产随机字符串
     */
    @Schema(name = "randomstr", description = "随机字符串不能为空")
    private String randomstr;

    /**
     * 订单商品名称，为空则以商户名作为商品名称
     */
    @Schema(name = "body", description = "订单商品名称,为空则以商户名作为商品名称")
    private String body;

    /**
     * 备注,最大160个字节(80个中文字符)
     * 禁止出现+，空格，/，?，%，#，&，=这几类特殊符号
     */
    @Schema(name = "remark", description = "禁止出现+，空格，/，?，%，#，&，=这几类特殊符号")
    private String remark;

    /**
     * 订单有效时间，以分为单位，不填默认为5分钟
     * 最大1440分钟
     */
    @Schema(name = "validtime", description = "订单有效时间")
    private Integer validtime;

    /**
     * 支付平台用户标识
     * JS支付时使用
     * 微信支付-用户的微信openid
     * 支付宝支付-用户user_id
     * 微信小程序-用户小程序的openid
     * 云闪付JS-用户userId
     */
    @Schema(name = "acct", description = "用户标识")
    private String acct;

    /**
     * 交易结果通知地址
     * 接收交易结果的通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * 因为刷卡支付交易结果实时返回,因此对于刷卡支付，该字段无效
     */
    @Schema(name = "notify_url", description = "交易结果通知地址")
    private String notify_url;

    /**
     * 支付限制，no_credit--指定不能使用信用卡支付
     * api暂时只对微信支付和支付宝有效,仅支持no_credit
     */
    @Schema(name = "limit_pay", description = "api暂时只对微信支付和支付宝有效,仅支持no_credit")
    private String limit_pay;

    /**
     * 微信子id，只对微信支付有效
     */
    @Schema(name = "sub_appid", description = "微信子id,只对微信支付有效")
    private String sub_appid;

    /**
     * 订单优惠标识，只对微信支付有效
     */
    @Schema(name = "goods_tag", description = "订单优惠标识，只对微信支付有效")
    private String goods_tag;

    /**
     * 微信单品优惠信息，json字符串
     */
    @Schema(name = "benefitdetail", description = "微信单品优惠信息,json字符串")
    private String benefitdetail;

    /**
     * 渠道门店编号
     * 例如对于支付宝支付，支付宝门店编号,对于微信支付，微信门店编号
     */
    @Schema(name = "chnlstoreid", description = "渠道门店编号")
    private String chnlstoreid;

    /**
     * 门店号
     */
    @Schema(name = "oldReqSn", description = "门店号")
    private String subbranch;

    /**
     * 拓展参数
     */
    @Schema(name = "extendparams", description = "拓展参数")
    private String extendparams;

    /**
     * 终端ip
     */
    @Schema(name = "cusip", description = "终端ip")
    private String cusip;

    /**
     * 支付跳转地址
     * 必须为https协议地址，且不允许带参数
     */
    @Schema(name = "front_url", description = "支付跳转地址")
    private String front_url;

    /**
     * 证件号
     * 实名交易必填.填了此字段就会验证证件号和姓名
     */
    @Schema(name = "idno", description = "证件号")
    private String idno;

    /**
     * 付款人真实姓名
     */
    @Schema(name = "truename", description = "付款人真实姓名")
    private String truename;

    /**
     * 分账信息
     * 格式:
     * cusid:type:amount;cusid:type:amount…
     * 其中
     * cusid:接收分账的通联商户号
     * type分账类型（01：按金额  02：按比率）
     * 如果分账类型为02，则分账比率为0.5表示50%。如果分账类型为01，则分账金额以元为单位表示
     */
    @Schema(name = "asinfo", description = "分账信息")
    private String asinfo;

    /**
     * 花呗分期
     */
    @Schema(name = "fqNum", description = "花呗分期")
    private String fqNum;
}
