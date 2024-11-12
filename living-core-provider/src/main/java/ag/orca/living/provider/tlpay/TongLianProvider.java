package ag.orca.living.provider.tlpay;

import ag.orca.living.api.tlpay.TongLianService;
import ag.orca.living.core.repo.tlpay.TongLianRepo;
import ag.orca.living.thirdparty.tlpay.enums.PaySceneEnum;
import ag.orca.living.thirdparty.tlpay.enums.RedirectPayTypeEnum;
import ag.orca.living.thirdparty.tlpay.enums.TlPayTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.TreeMap;

/**
 * <p>
 * 通联支付ServiceImpl
 * </p>
 */
@Slf4j
@DubboService
public class TongLianProvider implements TongLianService {

    @Resource
    TongLianRepo tongLianRepo;


    /**
     * 获取统一支付信息
     *
     * @param orderId         订单号 参数
     * @param payScene        支付场景
     * @param redirectPayType 跳转类型
     * @return String
     */
    @Override
    public Pair<Long, String> unifiedPay(Long orderId, Integer payScene, Integer redirectPayType) {
        PaySceneEnum paySceneEnum = PaySceneEnum.fromCode(payScene);
        RedirectPayTypeEnum redirectPayTypeEnum = RedirectPayTypeEnum.fromCode(redirectPayType);
        TlPayTypeEnum tlPayType = paySceneEnum.getPayType();
        return tongLianRepo.unifiedPay(orderId, paySceneEnum, tlPayType, redirectPayTypeEnum.getCode());
    }


    @Override
    public void payNotify(TreeMap<String, String> params, Long orderId) {
        tongLianRepo.payNotify(params, orderId);
    }


    @Override
    public String redirectUrl(Long orderId) {
        return tongLianRepo.redirectUrl(orderId);
    }
}
