package ag.orca.living.core.convert;

import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.thirdparty.tlpay.TlCancelParam;
import ag.orca.living.core.thirdparty.tlpay.TlQueryParam;
import ag.orca.living.core.thirdparty.tlpay.TlRefundParam;
import ag.orca.living.core.thirdparty.tlpay.TlUnifiedPayParam;

public class PayInfoConvert {


    public static TlUnifiedPayParam buildUnifiedPayParam(OrderInfo orderInfo,
                                                         UserInfo userInfo,
                                                         SysTlpay sysTlpay,
                                                         String randomStr,
                                                         String tlPayType) {
        return TlUnifiedPayParam.builder()
                //支付金额x100 转为分
                .trxamt(orderInfo.getPrice())
                //支付单号
                .reqsn(orderInfo.getId().toString())
                //随机字符串
                .randomstr(randomStr)
                //支付成功回调URL 需要拼接好支付单号
                .notify_url(sysTlpay.getNotify() + "/" + orderInfo.getId())
                //商户号
                .cusid(sysTlpay.getCusId())
                //AppId
                .appid(sysTlpay.getAppId())
                //微信子id，只对微信支付有效
                .sub_appid(sysTlpay.getWxAppId())
                //接口版本号
                .version(sysTlpay.getVersion())
                //签名方式
                .signtype(sysTlpay.getSignType())
                //30分超时
                .validtime(sysTlpay.getValid())
                //支付平台用户标识
                .acct(userInfo.getOpenId())
                //订单商品名称，为空则以商户名作为商品名称
                //param.setBody("");
                //支付场景
                .paytype(tlPayType)
                .build();

    }

    public static TlCancelParam buildCancelParam(OrderInfo orderInfo,
                                                 SysTlpay sysTlpay,
                                                 String randomStr) {
        return TlCancelParam.builder()
                //商户号
                .cusid(sysTlpay.getCusId())
                //AppId
                .appid(sysTlpay.getAppId())
                //交易订单号
                .reqsn(orderInfo.getId().toString())
                .oldreqsn(orderInfo.getId().toString())
                //支付流水号
                .oldtrxid(orderInfo.getTransactionId())
                //交易金额[单位为分]
                .trxamt(orderInfo.getPrice())
                //随机字符串
                .randomstr(randomStr)
                //签名类型
                .signtype(sysTlpay.getSignType())
                .build();
    }

    public static TlRefundParam buildRefundParam(OrderInfo orderInfo,
                                                 SysTlpay sysTlpay,
                                                 String randomStr) {
        return TlRefundParam.builder()
                //商户号
                .cusid(sysTlpay.getCusId())
                //AppId
                .appid(sysTlpay.getAppId())
                //交易订单号
                .reqsn(orderInfo.getId().toString())
                //支付单号
                .oldreqsn(orderInfo.getId().toString())
                //退款金额[单位为分]
                .trxamt(orderInfo.getPrice())
                //随机字符串
                .randomstr(randomStr)
                //签名类型
                .signtype(sysTlpay.getSignType())
                .build();
    }


    public static TlQueryParam buildQueryParam(OrderInfo orderInfo, SysTlpay sysTlpay, String randomStr) {
        return TlQueryParam.builder()
                //商户号
                .cusid(sysTlpay.getCusId())
                //AppId
                .appid(sysTlpay.getAppId())
                //交易订单号
                .reqsn(orderInfo.getId().toString())
                //随机字符串
                .randomstr(randomStr)
                //签名类型
                .signtype(sysTlpay.getSignType())
                .build();

    }
}
