package ag.orca.living.api.tlpay;

import org.apache.commons.lang3.tuple.Pair;

import java.util.TreeMap;

/**
 * <p>
 * 通联支付Service
 * </p>
 */
public interface TongLianService {
    /**
     * 获取统一支付信息
     *
     * @param orderId,     订单号
     * @param paySceneEnum 支付场景
     * @param payType
     * @param callBackType
     * @return String
     */
    Pair<Long, String> unifiedPay(Long orderId, Integer payType, Integer callBackType);


//    /**
//     * 取消订单接口
//     * @param orderId 订单号
//     * @return TlCancelResult
//     */
//    TlCancelResult cancel(Long orderId);

    //
//    /**
//     * 统一退款接口
//     * @param orderId 订单号
//     * @return TlRefundResult
//     */
//    TlRefundResult refund(Long orderId);


    /**
     * 统一支付回调
     *
     * @param params  回调参数
     * @param orderId orderId订单号
     * @return TlQueryResult
     */
    void payNotify(TreeMap<String, String> params, Long orderId);


    /**
     * 移动端获取重定向地址
     */
    String redirectUrl(Long orderId);

}
