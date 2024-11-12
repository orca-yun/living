package ag.orca.living.core.repo.control;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ControlAtmosphereConvert;
import ag.orca.living.core.dao.control.ControlAtmosphereFieldMapper;
import ag.orca.living.core.entity.control.ControlAtmosphereField;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.room.LivingRoomMarketGiftItem;
import ag.orca.living.core.enums.ControlExecStatusEnum;
import ag.orca.living.core.enums.ControlMessageTypeEnum;
import ag.orca.living.core.enums.ControlTypeEnum;
import ag.orca.living.core.event.ControlMessageEvent;
import ag.orca.living.core.repo.im.LivingImRepo;
import ag.orca.living.core.repo.market.LivingMarketRepo;
import ag.orca.living.core.repo.org.OrgRobotRepo;
import ag.orca.living.core.repo.room.LivingRoomMarketGiftItemRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.ro.control.SendAtmosphereMessageRo;
import ag.orca.living.core.ro.market.RobotGiftSendRo;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pulsar.client.api.Producer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 
 * 
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AtmosphereControlRepo {

    private static final int ROBOT_PAGE_SIZE = 200;

    private static final String ATM_CONTROL_EXEC_STATUS = "ATM_CONTROL_EXEC_STATUS:";

    private final StringRedisTemplate stringRedisTemplate;

    private final Producer<ControlMessageEvent> controlMessageEventProducer;

    private final LivingRoomRepo livingRoomRepo;

    private final LivingRoomMarketGiftItemRepo livingRoomMarketGiftItemRepo;

    private final OrgRobotRepo orgRobotRepo;

    private final LivingImRepo livingImRepo;

    private final LivingMarketRepo livingMarketRepo;

    private final ControlAtmosphereFieldMapper controlAtmosphereFieldMapper;

    /**
     * 保存氛围场控，并生成消息
     *
     * @param ro 发送氛围消息入参
     * @return 氛围场控
     */
    public ControlAtmosphereField saveAtmosphereAndGeneMsg(SendAtmosphereMessageRo ro) {
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(ro.getRoomId());
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingRoom room = roomOptional.get();

        if (isNotEmpty(ro.getGiftIdList())) {
            List<LivingRoomMarketGiftItem> gifts = livingRoomMarketGiftItemRepo.findByIds(ro.getGiftIdList());
            OrcaAssert.match(ro.getGiftIdList().size() != gifts.size(),
                    I18nUtil.getMessage("gift.not.exists"));
        }

        List<OrgRobot> randomRobots = getRandomRobots(room.getOrgId());
        OrcaAssert.match(isEmpty(randomRobots), I18nUtil.getMessage("robot.not.exists"));

        ControlAtmosphereField atmosphereField = ControlAtmosphereConvert.roToEntity(ro, room.getOrgId());
        controlAtmosphereFieldMapper.insertSelective(atmosphereField);

        // 生成消息
        LocalDateTime expEndTime = generateMessages(ro, randomRobots, atmosphereField);
        // 更新氛围场控预计结束时间
        controlAtmosphereFieldMapper.updateByPrimaryKeySelective(ControlAtmosphereField.builder()
                .id(atmosphereField.getId())
                .expEndTime(expEndTime)
                .build());

        atmosphereField.setExpEndTime(expEndTime);
        return atmosphereField;
    }

    private LocalDateTime generateMessages(SendAtmosphereMessageRo ro,
                                           List<OrgRobot> randomRobots,
                                           ControlAtmosphereField atmosphereField) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextTextTime = now.plusSeconds(2);
        LocalDateTime nextGiftTime = now.plusSeconds(3);

        OrgRobot randomRobot;
        ControlMessageEvent controlMessage;

        for (int i = 0; i < ro.getQuantity(); i++) {
            randomRobot = getRandomElement(randomRobots);
            controlMessage = buildMessage(ro, atmosphereField, randomRobot);

            if (isNotEmpty(ro.getTextContentList()) && isNotEmpty(ro.getGiftIdList())) {
                if (nextTextTime.isBefore(nextGiftTime)) {
                    // 文本
                    controlMessage.setMessageType(ControlMessageTypeEnum.TEXT.getCode());
                    controlMessage.setTriggerTime(nextTextTime);
                    controlMessage.setMessage(getRandomElement(ro.getTextContentList()));
                    nextTextTime = nextTextTime.plus(
                            ro.getTextInterval().multiply(new BigDecimal(1000)).longValue(),
                            ChronoUnit.MILLIS);
                } else {
                    // 礼物
                    controlMessage.setMessageType(ControlMessageTypeEnum.GIFT.getCode());
                    controlMessage.setTriggerTime(nextGiftTime);
                    controlMessage.setMessage(String.valueOf(getRandomElement(ro.getGiftIdList())));
                    nextGiftTime = nextGiftTime.plus(
                            ro.getGiftInterval().multiply(new BigDecimal(1000)).longValue(),
                            ChronoUnit.MILLIS);
                }
            } else if (isEmpty(ro.getTextContentList())) {
                // 礼物
                controlMessage.setMessageType(ControlMessageTypeEnum.GIFT.getCode());
                controlMessage.setTriggerTime(nextGiftTime);
                controlMessage.setMessage(String.valueOf(getRandomElement(ro.getGiftIdList())));
                nextGiftTime = nextGiftTime.plus(
                        ro.getGiftInterval().multiply(new BigDecimal(1000)).longValue(),
                        ChronoUnit.MILLIS);
            } else {
                // 文本
                controlMessage.setMessageType(ControlMessageTypeEnum.TEXT.getCode());
                controlMessage.setTriggerTime(nextTextTime);
                controlMessage.setMessage(getRandomElement(ro.getTextContentList()));
                nextTextTime = nextTextTime.plus(
                        ro.getTextInterval().multiply(new BigDecimal(1000)).longValue(),
                        ChronoUnit.MILLIS);
            }

            try {
                // 发送定时消息
                sendDelayedMessageAsync(controlMessage);
            } catch (Exception e) {
                log.warn("发送定时消息异常, roomId: {}", controlMessage.getRoomId(), e);
                break;
            }
        }

        return nextTextTime.isBefore(nextGiftTime) ? nextGiftTime : nextTextTime;
    }

    /**
     * 发送定时场控消息
     *
     * @param controlMessage 场控消息事件
     */
    private void sendDelayedMessageAsync(final ControlMessageEvent controlMessage) {
        // 查询氛围场控执行状态
        Integer execStatus = getAtmosphereExecStatusCache(controlMessage.getControlId());
        OrcaAssert.match(ControlExecStatusEnum.STOPPED.getCode().equals(execStatus),
                I18nUtil.getMessage("field.control.stopped"));

        controlMessageEventProducer.newMessage()
                .deliverAt(dateTimeToEpochMilli(controlMessage.getTriggerTime()))
                .value(controlMessage)
                .sendAsync()
                .thenAccept(messageId ->
                        log.info("[氛围场控] roomId: {}, msg: {}",
                                controlMessage.getRoomId(), JsonUtil.beanToJson(controlMessage)));
    }

    /**
     * 获取氛围场控执行状态
     *
     * @param id 氛围场控ID
     * @return 氛围场控执行状态
     */
    public Integer getAtmosphereExecStatusCache(Long id) {
        String key;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key = buildKey(id)))) {
            String cache = stringRedisTemplate.opsForValue().get(key);
            if (isNotBlank(cache)) {
                return Integer.valueOf(cache);
            }
        }

        ControlAtmosphereField atmosphereField = controlAtmosphereFieldMapper.selectByPrimaryKey(id);
        if (nonNull(atmosphereField)
                && nonNull(atmosphereField.getExecStatus())) {
            stringRedisTemplate.opsForValue().set(key,
                    String.valueOf(atmosphereField.getExecStatus()), Duration.ofMinutes(1));
        }

        return isNull(atmosphereField) ? null : atmosphereField.getExecStatus();
    }

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime 待转换的 LocalDateTime
     * @return LocalDateTime 对应的时间戳
     */
    private static long dateTimeToEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static ControlMessageEvent buildMessage(SendAtmosphereMessageRo ro,
                                                    ControlAtmosphereField atmosphereField,
                                                    OrgRobot randomDigitalHuman) {
        return ControlMessageEvent.builder()
                .orgId(atmosphereField.getOrgId())
                .roomId(ro.getRoomId())
                .robotId(randomDigitalHuman.getId())
                .robotNickname(randomDigitalHuman.getNickname())
                .robotHeadIco(randomDigitalHuman.getHeadIco())
                .controlType(ControlTypeEnum.ATMOSPHERE_FIELD_CONTROL.getCode())
                .controlId(atmosphereField.getId())
                .build();
    }


    private List<OrgRobot> getRandomRobots(Long orgId) {
        int totalRobots = orgRobotRepo.countByOrgId(orgId);
        OrcaAssert.match(totalRobots == 0, I18nUtil.getMessage("robot.not.exists"));

        int randomPage = 1;
        if (totalRobots > ROBOT_PAGE_SIZE) {
            randomPage = new Random().nextInt(totalRobots / ROBOT_PAGE_SIZE) + 1;
        }

        // 随机取一页机器人数据
        Pair<List<OrgRobot>, Long> pair = orgRobotRepo.findPageListByOrgId(randomPage, ROBOT_PAGE_SIZE, orgId);
        return pair.getLeft();
    }

    /**
     * 从一个集合中随机获取一个元素
     *
     * @param list 集合
     * @param <T>  集合中每个元素的类型
     * @return 集合中任意一个元素
     */
    private static <T> T getRandomElement(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }

        return list.get(new Random().nextInt(list.size()));
    }

    /**
     * 发送场控消息
     *
     * @param messageEvent 场控消息事件
     */
    public void sendMessage(ControlMessageEvent messageEvent) {
        if (Objects.isNull(messageEvent) || controlIsStop(messageEvent)) {
            return;
        }
        ControlMessageTypeEnum type = ControlMessageTypeEnum.ofCode(messageEvent.getMessageType());
        if (type == ControlMessageTypeEnum.GIFT) {
            // 发送礼物消息
            livingMarketRepo.robotSendGift(RobotGiftSendRo.builder()
                    .id(Long.valueOf(messageEvent.getMessage()))
                    .roomId(messageEvent.getRoomId())
                    .robotId(messageEvent.getRobotId())
                    .build());
        } else {
            // 发送文本消息
            livingImRepo.robotSendMsg(messageEvent.getRoomId(), messageEvent.getRobotId(), messageEvent.getMessage(),
                    null);
        }

    }

    /**
     * 发送前的检查
     *
     * @param messageEvent 场控消息事件
     */
    private boolean controlIsStop(ControlMessageEvent messageEvent) {
        Integer execStatus = getAtmosphereExecStatusCache(messageEvent.getControlId());
        return ControlExecStatusEnum.STOPPED.getCode().equals(execStatus);
    }

    /**
     * 查询直播间最新一条场控
     *
     * @param roomId 直播间ID
     * @return 直播间最新一条场控
     */
    public Optional<ControlAtmosphereField> getLatestControlByRoomId(Long roomId) {
        ControlAtmosphereField atmosphereField = controlAtmosphereFieldMapper.findLatestControlByRoomId(roomId);
        return Optional.ofNullable(atmosphereField);
    }

    /**
     * 停止直播间氛围场控
     *
     * @param id 氛围场控ID
     */
    public void stopAtmControl(Long id) {
        // 根据场控ID查询场控
        ControlAtmosphereField atmosphereField = controlAtmosphereFieldMapper.selectByPrimaryKey(id);
        OrcaAssert.isNull(atmosphereField, I18nUtil.getMessage("field.control.not.exists"));
        OrcaAssert.match(ControlExecStatusEnum.STOPPED.getCode()
                .equals(atmosphereField.getExecStatus()), I18nUtil.getMessage("field.control.stopped"));

        // 更新氛围场控执行状态为已停止
        controlAtmosphereFieldMapper.updateExecStatusStoppedById(id);
        // 清除氛围场控执行状态对应的 redis 缓存
        stringRedisTemplate.delete(buildKey(id));
    }

    /**
     * 构建氛围场控执行状态缓存key
     *
     * @param id 氛围场控ID
     * @return
     */
    private static String buildKey(Long id) {
        return ATM_CONTROL_EXEC_STATUS + id;
    }

}
