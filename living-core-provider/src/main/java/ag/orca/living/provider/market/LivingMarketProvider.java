package ag.orca.living.provider.market;

import ag.orca.living.api.market.LivingMarketService;
import ag.orca.living.core.repo.market.LivingMarketRepo;
import ag.orca.living.core.ro.market.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class LivingMarketProvider implements LivingMarketService {


    @Resource
    LivingMarketRepo livingMarketRepo;


    @Override
    public void giftAction(GiftActionRo ro) {
        livingMarketRepo.giftAction(ro);
    }

    @Override
    public void goodRecommend(GoodsRecommendRo ro) {
        livingMarketRepo.goodRecommend(ro);
    }

    @Override
    public void goodsAction(GoodsActionRo ro) {
        livingMarketRepo.goodsAction(ro);
    }

    @Override
    public void normalSendGift(GiftSendRo ro, String uid, Long channelId) {
        livingMarketRepo.normalSendGift(ro, uid, channelId);
    }

    @Override
    public void robotSendGift(RobotGiftSendRo ro) {
        livingMarketRepo.robotSendGift(ro);
    }


    @Override
    public void sortedGiftAction(GiftSortedRo ro) {
        livingMarketRepo.sortedGiftAction(ro.getRoomId(), ro.getItemIds());
    }

    @Override
    public void sortedGoodsAction(GoodSortedRo ro) {
        livingMarketRepo.sortedGoodsAction(ro.getRoomId(), ro.getItemIds());
    }

    @Override
    public void goodsBatchAction(GoodsBatchActionRo ro) {
        livingMarketRepo.goodsBatchAction(ro);
    }
}
