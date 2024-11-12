package ag.orca.living.core.repo;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.OrderInfoConvert;
import ag.orca.living.core.convert.PayInfoConvert;
import ag.orca.living.core.dao.order.OrderInfoMapper;
import ag.orca.living.core.dao.sys.SysTlpayMapper;
import ag.orca.living.core.entity.order.OrderInfo;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.entity.sys.SysTlpay;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.thirdparty.tlpay.TlQueryParam;
import ag.orca.living.core.thirdparty.tlpay.TlQueryResult;
import ag.orca.living.errors.OrcaException;
import ag.orca.living.thirdparty.tlpay.utils.HttpUtils;
import ag.orca.living.thirdparty.tlpay.utils.TlUtils;
import ag.orca.living.util.JsonUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.thirdparty.tlpay.utils.TlUtils.randomStr;

@Slf4j
@Repository
public class TongLianPayRepo {
    @Resource
    SysTlpayMapper sysTlpayMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;


    private Optional<SysTlpay> findTlpayConf(Long id) {
        return CommonConvert
                .filter(sysTlpayMapper.findAll(), s -> s.getId().equals(id))
                .stream().findFirst();
    }

    public void processOrgOrder(LocalDateTime trigger, Organization organization) {
        //查询当日订单状态是待支付的订单
        List<OrderInfo> orderInfos = orderInfoMapper.findListByOrgIdAndCondition(organization.getId(),
                null, null, OrderStatusEnum.INIT.getCode(), null, null);
        orderInfos.forEach(orderInfo -> {
            Optional<SysTlpay> optional = findTlpayConf(orderInfo.getTlPayId());
            optional.ifPresent(sysTlpay -> {
                TlQueryParam param = PayInfoConvert.buildQueryParam(orderInfo, sysTlpay, randomStr());
                JSONObject json = buildJsonSign(param, sysTlpay);
                //发送请求
                HttpUtils.postParamSend(sysTlpay.getApiBaseUrl() + "/query", json, new JSONObject(), TlQueryResult.class, (res, clazz) -> {
                    TlQueryResult r = JsonUtil.jsonToBean(res, clazz);
                    OrcaAssert.match(r.isFailed(), r.getRetmsg());
                    OrderStatusEnum orderStatus = null;
                    if (r.isTrxSuccess()) {
                        orderStatus = OrderStatusEnum.PAYED;
                    } else if (r.isTrxTimeout()) {
                        orderStatus = OrderStatusEnum.TIMEOUT;
                    }
                    OrcaAssert.match(Objects.isNull(orderStatus), r.getRetmsg());
                    log.info("订单: {}, 支付结果 {}", orderInfo.getId(), orderStatus);
                    OrderInfo updateOrder = OrderInfoConvert.queryToOrderBuild(orderInfo.getId(), r, orderStatus);
                    updateOrder.setCreateTime(orderInfo.getCreateTime());
                    orderInfoMapper.update(updateOrder);
                    return r;
                });

            });
        });
    }

    private <T> JSONObject buildJsonSign(T t, SysTlpay sysTlpay) {
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(t));
        try {
            String sign = TlUtils.createSign(json, sysTlpay.getMchRsaPriKey(), sysTlpay.getSignType());
            json.put("sign", sign);
            return json;
        } catch (Exception e) {
            throw OrcaException.error("通联加签失败" + e.getMessage());
        }
    }

}
