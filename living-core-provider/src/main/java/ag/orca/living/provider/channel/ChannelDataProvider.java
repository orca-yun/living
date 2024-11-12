package ag.orca.living.provider.channel;

import ag.orca.living.api.channel.ChannelDataService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.channel.DayLiveRoomChannelData;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.repo.channel.ChannelDataRepo;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.ro.query.QueryChannelDimensionRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionDetailRo;
import ag.orca.living.core.ro.query.QueryChannelTimeDimensionRo;
import ag.orca.living.core.ro.query.QueryLivingDimensionRo;
import ag.orca.living.core.vo.channel.ChannelDataBoardVo;
import ag.orca.living.core.vo.channel.ChannelDataDetailVo;
import ag.orca.living.core.vo.channel.ChannelRoomDetailVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@DubboService
public class ChannelDataProvider implements ChannelDataService {

    @Resource
    ChannelDataRepo channelDataRepo;

    @Resource
    LivingLiveRepo livingLiveRepo;

    @Resource
    LivingRoomRepo livingRoomRepo;


    @Override
    public Pair<List<ChannelDataDetailVo>, Long> findPageByLivingDimension(QueryLivingDimensionRo ro, Long orgId) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<ChannelDataDetailVo> vos = channelDataRepo.findPageByLivingDimension(orgId, ro.getRoomId(), ro.getFlow(), ro.getAmount());
        PageInfo<ChannelDataDetailVo> pageInfo = new PageInfo<>(vos);
        List<ChannelDataDetailVo> vv = CommonConvert.map(pageInfo.getList(), s -> s);
        return Pair.of(vv, pageInfo.getTotal());
    }

    @Override
    public ChannelDataBoardVo livingDimensionBoard(Long orgId, Long roomId) {
        return channelDataRepo.livingDimensionBoard(orgId, roomId);
    }


    @Override
    public Pair<List<ChannelDataDetailVo>, Long> findPageByChannelDimension(QueryChannelDimensionRo ro, Long orgId) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<ChannelDataDetailVo> vos = channelDataRepo.findPageByChannelDimension(orgId, ro.getChannelId(), ro.getFlow(), ro.getAmount());
        PageInfo<ChannelDataDetailVo> pageInfo = new PageInfo<>(vos);
        List<ChannelDataDetailVo> vv = CommonConvert.map(pageInfo.getList(), s -> s);
        return Pair.of(vv, pageInfo.getTotal());
    }

    @Override
    public ChannelDataBoardVo channelDimensionBoard(Long orgId, Long channelId) {
        return channelDataRepo.channelDimensionBoard(orgId, channelId);
    }

    @Override
    public Pair<List<ChannelDataDetailVo>, Long> findPageByTimeDimension(QueryChannelTimeDimensionRo ro, Long orgId) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<ChannelDataDetailVo> vos = channelDataRepo.findPageByTimeDimension(orgId, ro.getDate(), ro.getFlow(), ro.getAmount());
        PageInfo<ChannelDataDetailVo> pageInfo = new PageInfo<>(vos);
        List<ChannelDataDetailVo> vv = CommonConvert.map(pageInfo.getList(), s -> s);
        return Pair.of(vv, pageInfo.getTotal());
    }


    @Override
    public ChannelDataBoardVo timeDimensionBoard(Long orgId, LocalDate date) {
        return channelDataRepo.timeDimensionBoard(orgId, date);
    }

    @Override
    public Pair<List<ChannelRoomDetailVo>, Long> timeDimensionRoomDetail(QueryChannelTimeDimensionDetailRo ro, Long orgId) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<DayLiveRoomChannelData> data = channelDataRepo.findPageByTimeDimensionRoomDetail(ro.getChannelId(), ro.getDate());
        PageInfo<DayLiveRoomChannelData> pageInfo = new PageInfo<>(data);

        List<Long> recordIds = CommonConvert.map(pageInfo.getList(), DayLiveRoomChannelData::getLiveRecordId);
        List<LivingRoomLiveRecord> records = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(recordIds)) {
            records.addAll(livingLiveRepo.findListByRecordIdList(recordIds));
        }

        List<ChannelRoomDetailVo> vos = CommonConvert.map(pageInfo.getList(), d -> {
            Optional<LivingRoomLiveRecord> o = Optional.empty();
            if (Objects.nonNull(d.getLiveRecordId())) {
                o = CommonConvert.filter(records, s -> s.getId().equals(d.getLiveRecordId())).stream().findFirst();
            }
            Optional<LivingRoom> lr = livingRoomRepo.findLivingRoomById(d.getRoomId());
            return ChannelRoomDetailVo.builder()
                    .id(d.getId())
                    .roomId(d.getRoomId())
                    .liveRecordId(d.getLiveRecordId())
                    .flowPeople(d.getFlowPeople())
                    .transactionAmount(d.getTransactionAmount())
                    .subCommission(d.getSubCommission())
                    .commissionRatio(d.getCommissionRatio())
                    .roomName(lr.map(LivingRoom::getName).orElse(""))
                    .beginTime(o.map(LivingRoomLiveRecord::getBeginTime).orElse(null))
                    .endTime(o.map(LivingRoomLiveRecord::getEndTime).orElse(null))
                    .build();
        });
        return Pair.of(vos, pageInfo.getTotal());
    }


}
