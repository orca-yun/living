package ag.orca.living.core.repo.control;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ControlScriptConvert;
import ag.orca.living.core.dao.control.ControlScriptMapper;
import ag.orca.living.core.entity.control.ControlScript;
import ag.orca.living.core.entity.org.OrgRobot;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.enums.ControlMessageTypeEnum;
import ag.orca.living.core.repo.im.LivingImRepo;
import ag.orca.living.core.repo.market.LivingMarketRepo;
import ag.orca.living.core.repo.org.OrgRobotRepo;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.ro.control.AddScriptRo;
import ag.orca.living.core.ro.control.UpdateScriptRo;
import ag.orca.living.util.I18nUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ag.orca.living.core.convert.ControlScriptConvert.entityToRobotGiftSendRo;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * 直播间剧本服务
 *
 * 
 * @date 2024-04-26
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class LivingRoomScriptRepo {

    private final ControlScriptMapper scriptMapper;

    private final LivingRoomRepo livingRoomRepo;

    private final OrgRobotRepo orgRobotRepo;

    private final LivingImRepo livingImRepo;

    private final LivingMarketRepo livingMarketRepo;

    @Transactional(rollbackFor = Exception.class)
    public void addScript(AddScriptRo ro) {
        Optional<LivingRoom> roomOptional = livingRoomRepo.findLivingRoomById(ro.getRoomId());
        OrcaAssert.match(roomOptional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingRoom room = roomOptional.get();

        List<OrgRobot> robots = orgRobotRepo.findByIds(ro.getRobotIds());
        OrcaAssert.match(isEmpty(robots), I18nUtil.getMessage("robot.not.exists"));
        List<ControlScript> scripts = CommonConvert.map(robots, s -> ControlScriptConvert.robotToScript(room, s));
        scriptMapper.insertBatch(scripts);
    }

    public List<ControlScript> listByRoomId(Long roomId) {
        return scriptMapper.selectByRoomId(roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(List<Long> ids) {
        scriptMapper.logicDel(ids, LocalDateTime.now());
    }


    public void batchToSend(List<Long> ids) {
        List<ControlScript> scripts = scriptMapper.selectListByIds(ids);
        for (ControlScript script : scripts) {
            if (StringUtils.isNotBlank(script.getContent())) {
                ControlMessageTypeEnum t = ControlMessageTypeEnum.ofCode(script.getMessageType());
                if (ControlMessageTypeEnum.GIFT == t) {
                    // 发送礼物消息
                    livingMarketRepo.robotSendGift(
                            entityToRobotGiftSendRo(Long.valueOf(script.getContent()), script));
                } else {
                    // 发送文本消息
                    livingImRepo.robotSendMsg(script.getRoomId(), script.getRobotId(), script.getContent(), null);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveScript(UpdateScriptRo ro) {
        // 查询剧本
        ControlScript script = scriptMapper.selectByPrimaryKey(ro.getId());
        OrcaAssert.isNull(script, I18nUtil.getMessage("script.not.exists"));
        // 更新剧本
        scriptMapper.updateByPrimaryKeySelective(ControlScriptConvert.roToEntity(script, ro));
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveScriptAndSend(UpdateScriptRo ro) {
        // 查询剧本
        ControlScript script = scriptMapper.selectByPrimaryKey(ro.getId());
        OrcaAssert.isNull(script, I18nUtil.getMessage("script.not.exists"));
        // 更新剧本
        scriptMapper.updateByPrimaryKeySelective(ControlScriptConvert.roToEntity(script, ro));
        ControlMessageTypeEnum t = ControlMessageTypeEnum.ofCode(ro.getMessageType());

        if (ControlMessageTypeEnum.GIFT == t) {
            // 发送礼物消息
            livingMarketRepo.robotSendGift(
                    entityToRobotGiftSendRo(Long.valueOf(ro.getContent()), script));
        } else {
            // 发送文本消息
            livingImRepo.robotSendMsg(script.getRoomId(), script.getRobotId(), ro.getContent(), null);
        }
    }


}
