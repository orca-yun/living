package ag.orca.living.core.repo;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.dao.channel.ChannelInfoMapper;
import ag.orca.living.core.dao.channel.DayChannelDataMapper;
import ag.orca.living.core.dao.channel.DayLivingRoomChannelDataMapper;
import ag.orca.living.core.dao.live.LivingRoomLiveRecordMapper;
import ag.orca.living.core.dao.order.OrderInfoMapper;
import ag.orca.living.core.dao.room.LivingRoomMapper;
import ag.orca.living.core.dao.share.UserChannelRelationMapper;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.channel.DayChannelData;
import ag.orca.living.core.entity.channel.DayLiveRoomChannelData;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.OrderStatusEnum;
import ag.orca.living.core.vo.stats.ChannelAmountStaticsVo;
import ag.orca.living.core.vo.stats.ChannelStaticsVo;
import ag.orca.living.core.vo.stats.ChannelUserStaticsVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ChannelStaticsRecordRepo {

    @Resource
    DayLivingRoomChannelDataMapper dayLivingRoomChannelDataMapper;

    @Resource
    DayChannelDataMapper dayChannelDataMapper;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @Resource
    ChannelInfoMapper channelInfoMapper;

    @Resource
    LivingRoomMapper livingRoomMapper;

    @Resource
    UserChannelRelationMapper userChannelRelationMapper;

    @Resource
    LivingRoomLiveRecordMapper liveRecordMapper;


    @Transactional(rollbackFor = Exception.class)
    public void processChannelStatics(LocalDateTime trigger, Organization organization) {
        Long orgId = organization.getId();
        LocalDate queryDate = trigger.toLocalDate();
        List<ChannelInfo> channelInfos = channelInfoMapper.findAllChannelByOrgId(orgId);
        List<LivingRoom> rooms = livingRoomMapper.findListByOrgId(orgId);
        List<ChannelUserStaticsVo> userStaticsVos =
                userChannelRelationMapper.findChannelStaticsByOrgIdAndBindDate(orgId, queryDate);
        List<ChannelAmountStaticsVo> amountStaticsVos =
                orderInfoMapper.findChannelStaticsByOrgIdAndOrderDateAndOrderStatus(orgId, queryDate, OrderStatusEnum.PAYED.getCode());
        Set<Long> channelIdSet = new HashSet<>();
        channelIdSet.addAll(CommonConvert.map(userStaticsVos, ChannelStaticsVo::getChannelId));
        channelIdSet.addAll(CommonConvert.map(amountStaticsVos, ChannelStaticsVo::getChannelId));
        List<ChannelInfo> filterChannelInfos = CommonConvert.filter(channelInfos, s -> channelIdSet.contains(s.getChannelId()));

        Set<Long> roomIdSet = new HashSet<>();
        roomIdSet.addAll(CommonConvert.map(userStaticsVos, ChannelStaticsVo::getRoomId));
        roomIdSet.addAll(CommonConvert.map(amountStaticsVos, ChannelStaticsVo::getRoomId));
        List<LivingRoom> filterRooms = CommonConvert.filter(rooms, s -> roomIdSet.contains(s.getId()));

        List<DayChannelData> dayChannelData = new ArrayList<>();
        List<DayLiveRoomChannelData> dayLiveRoomChannelData = new ArrayList<>();
        filterChannelInfos.forEach(channelInfo -> {
            List<DayLiveRoomChannelData> liveRoomItems = filterRooms.stream().map(r -> {
                Integer flowPeople = userStaticsVos.stream()
                        .filter(u -> u.getRoomId().equals(r.getId()) && channelInfo.getChannelId().equals(u.getChannelId()))
                        .findFirst().map(ChannelUserStaticsVo::getCnt).orElse(0);
                ChannelAmountStaticsVo amt = amountStaticsVos.stream()
                        .filter(u -> u.getRoomId().equals(r.getId()) && channelInfo.getChannelId().equals(u.getChannelId()))
                        .findFirst().orElse(ChannelAmountStaticsVo.builder().realAmt(0L).subCommission(0L).build());
                List<LivingRoomLiveRecord> records = liveRecordMapper.findLiveRecordsByRoomIdAndStartDate(r.getId(), queryDate);
                OptionalLong optionalLong = CollectionUtils.isNotEmpty(records)
                        ? records.stream().mapToLong(LivingRoomLiveRecord::getId).max()
                        : OptionalLong.empty();
                return DayLiveRoomChannelData.builder()
                        .roomId(r.getId())
                        .liveRecordId(optionalLong.isEmpty() ? -1L : optionalLong.getAsLong())
                        .channelId(channelInfo.getChannelId())
                        .channelName(channelInfo.getChannelName())
                        .commissionRatio(channelInfo.getCommissionRatio())
                        .flowPeople(flowPeople)
                        .transactionAmount(amt.getRealAmt())
                        .subCommission(amt.getSubCommission())
                        .day(queryDate)
                        .build();
            }).collect(Collectors.toList());
            dayLiveRoomChannelData.addAll(liveRoomItems);

            Integer totalFlowPeople = liveRoomItems.stream().mapToInt(DayLiveRoomChannelData::getFlowPeople).sum();
            double avgCommissionRatio = liveRoomItems.stream().mapToInt(DayLiveRoomChannelData::getCommissionRatio).average().orElse(0d);
            Long totalAmount = liveRoomItems.stream().mapToLong(DayLiveRoomChannelData::getTransactionAmount).sum();
            Long totalSubCommission = liveRoomItems.stream().mapToLong(DayLiveRoomChannelData::getSubCommission).sum();
            DayChannelData channelData = DayChannelData.builder()
                    .orgId(channelInfo.getOrgId())
                    .channelId(channelInfo.getChannelId())
                    .channelName(channelInfo.getChannelName())
                    .flowPeople(totalFlowPeople)
                    .commissionRatio((int) avgCommissionRatio)
                    .transactionAmount(totalAmount)
                    .subCommission(totalSubCommission)
                    .day(queryDate)
                    .build();
            dayChannelData.add(channelData);
        });

        if (CollectionUtils.isNotEmpty(dayLiveRoomChannelData)) {
            dayLivingRoomChannelDataMapper.batchInsertOrUpdate(dayLiveRoomChannelData);
        }
        if (CollectionUtils.isNotEmpty(dayChannelData)) {
            dayChannelDataMapper.batchInsertOrUpdate(dayChannelData);
        }

    }


}
