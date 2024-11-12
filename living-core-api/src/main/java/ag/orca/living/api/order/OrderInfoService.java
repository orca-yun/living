package ag.orca.living.api.order;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.ro.order.OrderInfoRo;
import ag.orca.living.core.ro.order.ShareOrderRo;
import ag.orca.living.core.ro.query.QueryOrderRo;
import ag.orca.living.core.vo.order.OrderInfoVo;
import ag.orca.living.core.vo.share.LivingShareUserAddressVo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface OrderInfoService {

    Pair<Long, Integer> createOrder(AuthInfo authInfo, Long userId, OrderInfoRo ro);

    /**
     * 统一查询接口
     *
     * @param orderId 订单号
     * @return TlQueryResult
     */
    Boolean orderIsPayed(Long orderId);

    LivingShareUserAddressVo queryUserAddress(Long userId);

    Pair<List<OrderInfoVo>, Long> findPageList(Long orgId, QueryOrderRo ro);

    List<OrderInfoVo> findListNoPage(Long orgId, QueryOrderRo ro);

    List<OrderInfoVo> findUserOrderListByRoomIdAndUid(Long orgId, ShareOrderRo orderRo);


    void cancelUnPayedOrder(Long orderId);
}
