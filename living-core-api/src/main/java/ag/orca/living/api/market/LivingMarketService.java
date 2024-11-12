package ag.orca.living.api.market;

import ag.orca.living.core.ro.market.*;

public interface LivingMarketService {
    void giftAction(GiftActionRo ro);

    void goodRecommend(GoodsRecommendRo ro);

    void goodsAction(GoodsActionRo ro);

    void normalSendGift(GiftSendRo ro, String uid, Long channelId);

    void robotSendGift(RobotGiftSendRo ro);

    void sortedGiftAction(GiftSortedRo ro);

    void sortedGoodsAction(GoodSortedRo ro);

    void goodsBatchAction(GoodsBatchActionRo ro);
}
