package ag.orca.living.core.repo.live;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.LivingRoomLiveRecordConvert;
import ag.orca.living.core.dao.live.LivingRoomLiveRecordMapper;
import ag.orca.living.core.entity.live.LivingRoomLiveRecord;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.LiveRecordStatusEnum;
import ag.orca.living.core.enums.LiveSourceEnum;
import ag.orca.living.core.event.LiveStatusNotifyEvent;
import ag.orca.living.core.repo.interact.BlacklistRepo;
import ag.orca.living.core.repo.interact.MuteRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.pulsar.client.api.Producer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.CacheConst.RECORD_ID_PREFIX;
import static ag.orca.living.common.OrcaConst.ZONE_OFFSET;

@Slf4j
@Repository
public class LivingLiveRepo {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    LivingRoomLiveRecordMapper liveRecordMapper;

    @Resource
    LivingRoomRepo livingRoomRepo;

    @DubboReference
    LivingSeqService livingSeqService;


    @Resource
    MuteRepo muteRepo;

    @Resource
    BlacklistRepo blacklistRepo;

    @Resource
    Producer<LiveStatusNotifyEvent> liveStatusNotifyEventProducer;


    public LivingRoomLiveRecord findLatestLiveRecord(Long roomId) {
        return liveRecordMapper.findLatestLiveRecord(roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void operateLiveStop(Long roomId,
                                LiveSourceEnum liveSource) {
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingRoom room = roomOptional.get();
        Optional<Long> optional = getLivingRecordIdByRoomId(roomId);
        optional.ifPresent(recordId -> {
            LivingRoomLiveRecord record = liveRecordMapper.findLiveRecordById(recordId);
            LiveSourceEnum source = LiveSourceEnum.ofCode(record.getLiveSource());
            OrcaAssert.match(source != liveSource, MessageFormat.format(I18nUtil.getMessage("room.record.live.stop.err"), source, liveSource));
            LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(record.getStatus());
            if (status == LiveRecordStatusEnum.living) {
                //关闭当前直播
                long cost = LocalDateTime.now().toEpochSecond(ZONE_OFFSET) - record.getBeginTime().toEpochSecond(ZONE_OFFSET);
                record.setCost(cost);
                record.setStopDate(LocalDate.now());
                record.setEndTime(LocalDateTime.now());
                record.setStatus(LiveRecordStatusEnum.lived.getCode());
                //清理数据
                liveRecordMapper.finishLivingRecord(record);
                // 更新房间上状态
                room.setStatus(record.getStatus());
                livingRoomRepo.update(room);
                // 清理 缓存中数据
                triggerCleanCacheByFinishLive(roomId);
                // 通知直播状态
                sendLivingNotify(roomId, record.getId(), LiveRecordStatusEnum.lived);
            }

        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void operateLiveStart(Long roomId,
                                 LiveSourceEnum liveSource,
                                 Integer needRecord) {
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingRoom room = roomOptional.get();
        Long recordId = livingSeqService.nextId();
        //是开播的话，插入库后立马执行 记录进入缓存
        LivingRoomLiveRecord record = LivingRoomLiveRecordConvert.buildEntityFromRoom(room, needRecord, liveSource);
        record.setId(recordId);
        liveRecordMapper.insert(record);

        // 更新房间的直播状态
        room.setStatus(record.getStatus());
        livingRoomRepo.update(room);

        // 开播记录的ID记录进入 缓存中
        stringRedisTemplate.opsForValue().set(RECORD_ID_PREFIX + roomId, recordId.toString());
        // 通知直播状态
        sendLivingNotify(roomId, recordId, LiveRecordStatusEnum.living);
    }

    private void sendLivingNotify(Long roomId, Long recordId, LiveRecordStatusEnum status) {
        LiveStatusNotifyEvent event = LiveStatusNotifyEvent.builder()
                .roomId(roomId).roomRecordId(recordId).status(status).build();
        liveStatusNotifyEventProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[直播状态通知] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));
    }


    private void triggerCleanCacheByFinishLive(Long roomId) {

        // 清理 黑名单
        blacklistRepo.cleanBlacklist(roomId);
        // 清理 禁言
        muteRepo.cleanMuteList(roomId);

        //清理 房间开播场景记录ID
        stringRedisTemplate.delete(RECORD_ID_PREFIX + roomId);

    }


    public Optional<LivingRoomLiveRecord> findLatestLiveRecordByRoomId(Long roomId) {
        Optional<Long> o = getLivingRecordIdByRoomId(roomId);
        return o.map(id -> liveRecordMapper.findLiveRecordById(id));
    }

    public Optional<Long> getLivingRecordIdByRoomId(Long roomId) {
        String k = RECORD_ID_PREFIX + roomId;
        String recordId = stringRedisTemplate.opsForValue().get(k);
        if (Objects.isNull(recordId)) {
            LivingRoomLiveRecord record = liveRecordMapper.findLatestLiveRecord(roomId);
            if (Objects.nonNull(record)) {
                LiveRecordStatusEnum status = LiveRecordStatusEnum.ofCode(record.getStatus());
                if (status == LiveRecordStatusEnum.living) {
                    stringRedisTemplate.opsForValue().set(k, record.getId().toString());
                    return Optional.of(record.getId());
                }
            }
        }
        return Optional.ofNullable(Objects.isNull(recordId) ? null : Long.parseLong(recordId));
    }

    public List<LivingRoomLiveRecord> findListByRecordIdList(List<Long> ids) {
        return liveRecordMapper.findListByRecordIdList(ids, null);
    }


}
