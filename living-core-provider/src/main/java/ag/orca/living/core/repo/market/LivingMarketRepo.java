package ag.orca.living.core.repo.market;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ActionEventRecordConvert;
import ag.orca.living.core.convert.LivingRoomMarketGiftItemConvert;
import ag.orca.living.core.convert.LivingRoomMarketGoodsItemConvert;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import ag.orca.living.core.entity.room.LivingRoomMarketGoodsItem;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.enums.CrossRoleEnum;
import ag.orca.living.core.enums.SellStatusEnum;
import ag.orca.living.core.event.GiftActionEvent;
import ag.orca.living.core.event.GiftSendEvent;
import ag.orca.living.core.event.GoodsActionEvent;
import ag.orca.living.core.event.UserSendStatEvent;
import ag.orca.living.core.mongo.GiftActionEventRecord;
import ag.orca.living.core.mongo.GoodsActionEventRecord;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.org.OrgRobotRepo;
import ag.orca.living.core.repo.room.LivingRoomMarketGiftItemRepo;
import ag.orca.living.core.repo.room.LivingRoomMarketGoodsItemRepo;
import ag.orca.living.core.repo.share.WxShareUserInfoRepo;
import ag.orca.living.core.ro.market.*;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.pulsar.client.api.Producer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.MongoConst.CTRL_GIFT_CONTROL;
import static ag.orca.living.common.MongoConst.CTRL_GOODS_CONTROL;

@Slf4j
@Repository
public class LivingMarketRepo {

    @Resource
    OrgRobotRepo orgRobotRepo;

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    LivingRoomMarketGoodsItemRepo livingRoomMarketGoodsItemRepo;

    @Resource
    LivingRoomMarketGiftItemRepo livingRoomMarketGiftItemRepo;

    @Resource
    Producer<GoodsActionEvent> goodsActionProducer;

    @Resource
    Producer<GiftActionEvent> giftActionProducer;

    @Resource
    Producer<GiftSendEvent> giftSendProducer;

    @Resource
    Producer<UserSendStatEvent> userSendStatEventProducer;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    WxShareUserInfoRepo wxShareUserInfoRepo;


    public void goodRecommend(GoodsRecommendRo ro) {
        // 商品推荐 【发MQ消息】，通知所有端
        Long roomId = ro.getRoomId();
        Optional<LivingRoomMarketGoodsItem> optional = livingRoomMarketGoodsItemRepo.findById(ro.getId());
        optional.ifPresent(item -> {
            SellStatusEnum sellStatus = SellStatusEnum.ofCode(item.getSellStatus());
            OrcaAssert.match(sellStatus != SellStatusEnum.ON_SHELVES, I18nUtil.getMessage("goods.sell.out"));
            GoodsActionEvent event = buildGoodActionEvent(roomId, item);
            // 保存数据进入 MONGO 同时发送消息出去
            saveAndSendGoodsAction(roomId, event);
        });
    }

    public void goodsAction(GoodsActionRo ro) {
        Long roomId = ro.getRoomId();
        Optional<LivingRoomMarketGoodsItem> optional = livingRoomMarketGoodsItemRepo.findById(ro.getId());
        optional.ifPresent(item -> {
            livingRoomMarketGoodsItemRepo.updateGoodsSellStatus(ro.getId(), ro.getSellStatus());
            GoodsActionEvent event = buildGoodActionEvent(ro.getRoomId(), null);
            // 保存数据进入 MONGO 同时发送消息出去
            saveAndSendGoodsAction(roomId, event);
        });
    }


    public void goodsBatchAction(GoodsBatchActionRo ro) {
        Long roomId = ro.getRoomId();
        List<LivingRoomMarketGoodsItem> items = livingRoomMarketGoodsItemRepo.findListByRoomId(roomId);
        SellStatusEnum status = SellStatusEnum.ofCode(ro.getSellStatus());
        SellStatusEnum filter = (status == SellStatusEnum.ON_SHELVES) ? SellStatusEnum.OFF_SHELVES : SellStatusEnum.ON_SHELVES;
        List<Long> modIds = items.stream().filter(s -> s.getSellStatus() == filter.getCode()).map(LivingRoomMarketGoodsItem::getId).toList();
        if (CollectionUtils.isNotEmpty(modIds)) {
            livingRoomMarketGoodsItemRepo.batchUpdateSellStatus(modIds, status);
            GoodsActionEvent event = buildGoodActionEvent(ro.getRoomId(), null);
            // 保存数据进入 MONGO 同时发送消息出去
            saveAndSendGoodsAction(roomId, event);
        }
    }

    public void sortedGoodsAction(Long roomId, List<Long> itemIds) {
        livingRoomMarketGoodsItemRepo.sortedGoods(roomId, itemIds);
        GoodsActionEvent event = buildGoodActionEvent(roomId, null);
        // 保存数据进入 MONGO 同时发送消息出去
        saveAndSendGoodsAction(roomId, event);
    }

    private void saveAndSendGoodsAction(Long roomId, GoodsActionEvent event) {
        Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
        roomRecord.ifPresent(rr -> saveGoodsActionEvent(event, roomId, rr));
        goodsActionProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[商品场控] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
    }

    private void saveGoodsActionEvent(GoodsActionEvent event, Long roomId, Long roomRecord) {
        GoodsActionEventRecord record = ActionEventRecordConvert.buildGoodsActionEventRecord(event);
        mongoTemplate.save(record, CTRL_GOODS_CONTROL + roomId + "_" + roomRecord);
    }


    public void giftAction(GiftActionRo ro) {
        Long roomId = ro.getRoomId();
        // 礼物发送 推荐 【发MQ消息】，通知所有端
        Optional<LivingRoomMarketGiftItem> optional = livingRoomMarketGiftItemRepo.findById(ro.getId());
        optional.ifPresent(item -> {
            livingRoomMarketGiftItemRepo.updateGiftStatus(ro.getId(), ro.getStatus());
            GiftActionEvent event = buildGiftActionEvent(roomId);
            // 保存数据进入 Mongo
            saveAndSendGiftAction(roomId, event);
        });
    }

    private void saveAndSendGiftAction(Long roomId, GiftActionEvent event) {
        Optional<Long> roomRecord = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
        roomRecord.ifPresent(rr -> saveGiftActionEvent(event, roomId, rr));
        giftActionProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[礼物场控] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
    }

    private void saveGiftActionEvent(GiftActionEvent event, Long roomId, Long roomRecord) {
        GiftActionEventRecord record = ActionEventRecordConvert.buildGiftActionEventRecord(event);
        mongoTemplate.save(record, CTRL_GIFT_CONTROL + roomId + "_" + roomRecord);
    }

    private GiftActionEvent buildGiftActionEvent(Long roomId) {
        GiftActionEvent event = new GiftActionEvent();
        event.setRoomId(roomId);
        List<LivingRoomMarketGiftItem> items = livingRoomMarketGiftItemRepo.findListByRoomIdForShare(roomId);
        event.setItems(CommonConvert.map(items, LivingRoomMarketGiftItemConvert::entityToVo));
        return event;
    }


    public void sortedGiftAction(Long roomId, List<Long> itemIds) {
        livingRoomMarketGiftItemRepo.sortedGift(roomId, itemIds);
        GiftActionEvent event = buildGiftActionEvent(roomId);
        // 保存数据进入 Mongo
        saveAndSendGiftAction(roomId, event);
    }


    public void normalSendGift(GiftSendRo ro, String uid, Long channelId) {
        Long roomId = ro.getRoomId();
        Optional<LivingRoomMarketGiftItem> optional = livingRoomMarketGiftItemRepo.findById(ro.getId());
        optional.ifPresent(item -> {
            // 礼物消息不做回放
            GiftSendEvent event = buildNormalGiftSendEvent(item, roomId, uid);
            // todo 保存到 mysql
            giftSendProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[礼物送出:普通] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
            UserSendStatEvent statEvent = buildGiftSendStat(item, uid, channelId);
            userSendStatEventProducer.sendAsync(statEvent).thenAccept(messageId ->
                    log.info("[MQ][消息统计] 用户发送消息触发统计 roomId: {}, msg: {}", roomId, statEvent));
        });
    }

    private UserSendStatEvent buildGiftSendStat(LivingRoomMarketGiftItem item, String uid, Long channelId) {
        UserSendStatEvent statEvent = new UserSendStatEvent();
        //消息触发
        statEvent.setType(2);
        statEvent.setOrgId(item.getOrgId());
        statEvent.setRoomId(item.getRoomId());
        statEvent.setChannelId(channelId);
        statEvent.setUid(uid);
        statEvent.setViewDate(LocalDate.now());
        return statEvent;
    }

    public void robotSendGift(RobotGiftSendRo ro) {
        Long roomId = ro.getRoomId();
        Optional<LivingRoomMarketGiftItem> optional = livingRoomMarketGiftItemRepo.findById(ro.getId());
        optional.ifPresent(item -> {
            // 礼物消息不做回放
            GiftSendEvent event = buildRobotGiftSendEvent(item, ro);
            // todo 保存到 mysql
            giftSendProducer.sendAsync(event)
                    .thenAccept(messageId ->
                            log.info("[礼物送出:机器人] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));

        });
    }


    private GiftSendEvent buildNormalGiftSendEvent(LivingRoomMarketGiftItem item, Long roomId, String shareUid) {
        GiftSendEvent event = new GiftSendEvent();
        event.setGiftItem(LivingRoomMarketGiftItemConvert.entityToVo(item));
        event.setRoomId(roomId);
        UserInfo userInfo = wxShareUserInfoRepo.findUserInfoByUserId(shareUid);
        event.setSenderUid(shareUid);
        event.setSenderType(CrossRoleEnum.SHARE.getCode());
        event.setSenderName(userInfo.getNickName());
        event.setSenderHeadIco(userInfo.getHeadImageUrl());
        return event;
    }


    private GiftSendEvent buildRobotGiftSendEvent(LivingRoomMarketGiftItem item, RobotGiftSendRo ro) {
        Optional<OrgRobot> robot = orgRobotRepo.findById(ro.getRobotId());
        GiftSendEvent event = new GiftSendEvent();
        event.setGiftItem(LivingRoomMarketGiftItemConvert.entityToVo(item));
        event.setRoomId(ro.getRoomId());
        event.setSenderType(CrossRoleEnum.ROBOT.getCode());
        if (robot.isEmpty()) {
            event.setSenderName("orca");
            event.setSenderHeadIco("");
        } else {
            OrgRobot r = robot.get();
            event.setSenderName(r.getNickname());
            event.setSenderHeadIco(r.getHeadIco());
            event.setSenderUid(r.getCode());
        }
        return event;
    }


    private GoodsActionEvent buildGoodActionEvent(Long roomId, LivingRoomMarketGoodsItem recommend) {
        GoodsActionEvent event = new GoodsActionEvent();
        event.setRoomId(roomId);
        event.setRecommend(Objects.isNull(recommend) ? null : LivingRoomMarketGoodsItemConvert.entityToVo(recommend));
        List<LivingRoomMarketGoodsItem> items = livingRoomMarketGoodsItemRepo.findListByRoomIdForShare(roomId);
        event.setItems(CommonConvert.map(items, LivingRoomMarketGoodsItemConvert::entityToVo));
        return event;
    }


}
