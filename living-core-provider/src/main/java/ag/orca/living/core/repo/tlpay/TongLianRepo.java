package ag.orca.living.core.repo.tlpay;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.config.E2eConfig;
import ag.orca.living.core.convert.PayInfoConvert;
import ag.orca.living.core.dao.sys.SysTlpayMapper;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.repo.order.OrderInfoRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.share.WxShareUserInfoRepo;
import ag.orca.living.core.thirdparty.tlpay.*;
import ag.orca.living.errors.OrcaException;
import ag.orca.living.thirdparty.tlpay.enums.PaySceneEnum;
import ag.orca.living.thirdparty.tlpay.enums.TlPayTypeEnum;
import ag.orca.living.thirdparty.tlpay.utils.HttpUtils;
import ag.orca.living.thirdparty.tlpay.utils.QRCodeUtil;
import ag.orca.living.thirdparty.tlpay.utils.TlUtils;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

import static ag.orca.living.common.OrcaConst.SPLIT;
import static ag.orca.living.thirdparty.tlpay.utils.TlUtils.randomStr;

@Slf4j
@Repository
public class TongLianRepo {

    @Resource
    OrderInfoRepo orderInfoRepo;

    @Resource
    WxShareUserInfoRepo wxShareUserInfoRepo;


    @Resource
    LivingRoomRepo livingRoomRepo;

    @Resource
    E2eConfig e2eConfig;

    @Resource
    SysTlpayMapper sysTlpayMapper;


    public SysTlpay randomTlConfig() {
        List<SysTlpay> tlpays = sysTlpayMapper.findAll();
        int n = new Random().nextInt(tlpays.size());
        return tlpays.get(n);
    }

    private SysTlpay findTlpayConf(Long id) {
        Optional<SysTlpay> optional = CommonConvert
                .filter(sysTlpayMapper.findAll(), s -> s.getId().equals(id))
                .stream().findFirst();
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("tl.pay.mch.not.exists"));
        return optional.get();
    }

    /**
     * 获取随机字符串
     *
     * @return UUID
     */

    public Pair<Long, String> unifiedPay(Long orderId,
                                         PaySceneEnum payScene,
                                         TlPayTypeEnum tlPayType,
                                         Integer callBackType) {

        //查询订单相关信息
        OrderInfo orderInfo = checkAndGetOrder(orderId);
        OrcaAssert.match(orderInfo.getTimeoutTime().isBefore(LocalDateTime.now()), I18nUtil.getMessage("order.timeout"));
        checkOrderStatusInInit(orderInfo);

        SysTlpay sysTlpay = findTlpayConf(orderInfo.getTlPayId());

        UserInfo userInfo = wxShareUserInfoRepo.findUserInfoByUserId(orderInfo.getUserId());

        //支付金额
        TlUnifiedPayParam param = PayInfoConvert.buildUnifiedPayParam(
                orderInfo,
                userInfo,
                sysTlpay,
                randomStr(),
                tlPayType.getCode());

        //获取加签Sign
        JSONObject json = buildJsonSign(param, sysTlpay);
        //发送请求
        TlUnifiedPayResult result = HttpUtils.postParamSend(sysTlpay.getApiBaseUrl() + "/pay", json, new JSONObject(), TlUnifiedPayResult.class, (res, clazz) -> {
            TlUnifiedPayResult r = JsonUtil.jsonToBean(res, clazz);
            OrcaAssert.match(r.isFailed(), r.getRetmsg());
            return r;
        });
        //如果微信扫码需要转成Base64的二维码
        if (PaySceneEnum.WX_SCAN == payScene) {
            return Pair.of(orderInfo.getPrice(), QRCodeUtil.encode(result.getPayinfo()));
        }
        return Pair.of(orderInfo.getPrice(), result.getPayinfo());
    }

    public void payNotify(TreeMap<String, String> params, Long orderId) {
        OrderInfo orderInfo = checkAndGetOrder(orderId);
        //校验签名
        try {
            //支付成功
            JSONObject json = new JSONObject(params);
            log.info("通联支付回调成功：{}, 结果 {}", orderId, json.toJSONString());
            TlPayNotifyResult param = JsonUtil.jsonToBean(json.toJSONString(), TlPayNotifyResult.class);
            OrcaAssert.match(!param.isTrxSuccess(), "通联支付回调失败 trxstatus: " + param.getTrxstatus());
            orderInfoRepo.paySuccess(orderInfo, param);
        } catch (Exception e) {
            throw OrcaException.error("通联回调处理失败" + e.getMessage());
        }
    }

    /**
     * https://aipboss.allinpay.com/know/devhelp/main.php?pid=38#mid=314
     * 使用场景：只能撤销当天的交易，全额退款，实时返回退款结果
     *
     * @param orderId
     */
    public void todayCancelPay(Long orderId) {
        //查询订单相关信息
        OrderInfo orderInfo = checkAndGetOrder(orderId);
        checkOrderStatusInInit(orderInfo);

        SysTlpay sysTlpay = findTlpayConf(orderInfo.getTlPayId());
        TlCancelParam param = PayInfoConvert.buildCancelParam(orderInfo, sysTlpay, randomStr());
        JSONObject json = buildJsonSign(param, sysTlpay);

        //发送请求
        HttpUtils.postParamSend(sysTlpay.getApiBaseUrl() + "/cancel", json, new JSONObject(), TlCancelResult.class, (res, clazz) -> {
            TlCancelResult r = JsonUtil.jsonToBean(res, clazz);
            OrcaAssert.match(r.isFailed(), r.getRetmsg());
            //订单成功撤销
            orderInfoRepo.refundPay(orderId);
            return r;
        });

    }


    /**
     * https://aipboss.allinpay.com/know/devhelp/main.php?pid=38#mid=315
     * 使用场景：支持部分金额退款，隔天交易退款。（建议在交易完成后间隔几分钟（最短5分钟）再调用退款接口，避免出现订单状态同步不及时导致退款失败。）
     *
     * @param orderId
     * @return
     */
    public TlRefundResult historyRefundPay(Long orderId) {
        //查询订单相关信息
        OrderInfo orderInfo = checkAndGetOrder(orderId);
        SysTlpay sysTlpay = findTlpayConf(orderInfo.getTlPayId());

        TlRefundParam param = PayInfoConvert.buildRefundParam(orderInfo, sysTlpay, randomStr());
        //获取加签Sign
        JSONObject json = buildJsonSign(param, sysTlpay);

        //发送请求
        return HttpUtils.postParamSend(sysTlpay.getApiBaseUrl() + "/refund", json, new JSONObject(), TlRefundResult.class, (res, clazz) -> {
            TlRefundResult r = JsonUtil.jsonToBean(res, clazz);
            OrcaAssert.match(r.isFailed(), r.getRetmsg());
            //订单退款撤销
            orderInfoRepo.refundPay(orderId);
            return r;
        });
    }


    public <T> JSONObject buildJsonSign(T t, SysTlpay sysTlpay) {
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(t));
        try {
            String sign = TlUtils.createSign(json, sysTlpay.getMchRsaPriKey(), sysTlpay.getSignType());
            json.put("sign", sign);
            return json;
        } catch (Exception e) {
            throw OrcaException.error("通联加签失败" + e.getMessage());
        }
    }


    public String redirectUrl(Long orderId) {
        OrderInfo orderInfo = checkAndGetOrder(orderId);
        Optional<LivingRoom> optional = livingRoomRepo.findLivingRoomById(orderInfo.getRoomId());
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.encode.key.error"));
        LivingRoom room = optional.get();
        String plain = orderInfo.getOrgId() +
                SPLIT + orderInfo.getRoomId() +
                SPLIT + room.getSharePwd() +
                SPLIT + orderInfo.getChannelId();
        String key = EncryptUtil.desEncrypt(plain);
        return MessageFormat.format(e2eConfig.getShareLiveFormat(), key);
    }

    private OrderInfo checkAndGetOrder(Long orderId) {
        OrderInfo orderInfo = orderInfoRepo.findOrderInfoById(orderId);
        OrcaAssert.match(Objects.isNull(orderInfo), I18nUtil.getMessage("order.not.exists.err"));
        return orderInfo;
    }

    private void checkOrderStatusInInit(OrderInfo orderInfo) {
        OrderStatusEnum orderStatus = OrderStatusEnum.ofCode(orderInfo.getOrderStatus());
        OrcaAssert.match(orderStatus != OrderStatusEnum.INIT, I18nUtil.getMessage("order.status.change.err"));
    }

}
