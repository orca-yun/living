package ag.orca.living.api.control;

import ag.orca.living.core.ro.control.ControlAtmosphereTemplateRo;
import ag.orca.living.core.ro.control.SendAtmosphereMessageRo;
import ag.orca.living.core.vo.control.ControlAtmosphereFieldVo;
import ag.orca.living.core.vo.control.ControlAtmosphereTemplateVo;

import java.util.List;

/**
 * 
 * 
 */
public interface AtmosphereControlService {

    /**
     * 保存氛围场控，并生成消息
     *
     * @param ro 发送氛围消息入参
     * @return 氛围场控
     */
    ControlAtmosphereFieldVo saveAtmosphereAndGeneMsg(SendAtmosphereMessageRo ro);

    /**
     * 查询直播间最新一条场控
     *
     * @param roomId 直播间ID
     * @return 直播间最新一条场控
     */
    ControlAtmosphereFieldVo getLatestControlByRoomId(Long roomId);

    /**
     * 停止直播间氛围场控
     *
     * @param id 氛围场控ID
     */
    void stopAtmControl(Long id);

    /**
     * 根据房间号或者这个房间配置所有的模板
     *
     * @param roomId
     * @return
     */
    List<ControlAtmosphereTemplateVo> findTemplateListByRoomId(Long roomId);

    /**
     * 删除该模板
     *
     * @param id
     */
    void removeTemplate(Long id);

    void saveTemplate(Long orgId, ControlAtmosphereTemplateRo ro);
}
