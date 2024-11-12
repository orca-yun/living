package ag.orca.living.provider.control;

import ag.orca.living.api.control.AtmosphereControlService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.ControlAtmosphereConvert;
import ag.orca.living.core.convert.ControlAtmosphereTemplateConvert;
import ag.orca.living.core.entity.control.ControlAtmosphereField;
import ag.orca.living.core.entity.control.ControlAtmosphereTemplate;
import ag.orca.living.core.repo.control.AtmosphereControlRepo;
import ag.orca.living.core.repo.control.AtmosphereTemplateRepo;
import ag.orca.living.core.ro.control.ControlAtmosphereTemplateRo;
import ag.orca.living.core.ro.control.SendAtmosphereMessageRo;
import ag.orca.living.core.vo.control.ControlAtmosphereFieldVo;
import ag.orca.living.core.vo.control.ControlAtmosphereTemplateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Optional;

/**
 * 
 * 
 */
@DubboService
@RequiredArgsConstructor
@Slf4j
public class AtmosphereControlProvider implements AtmosphereControlService {

    private final AtmosphereControlRepo atmosphereControlRepo;

    private final AtmosphereTemplateRepo atmosphereTemplateRepo;

    @Override
    public ControlAtmosphereFieldVo saveAtmosphereAndGeneMsg(SendAtmosphereMessageRo ro) {
        ControlAtmosphereField field = atmosphereControlRepo.saveAtmosphereAndGeneMsg(ro);
        return ControlAtmosphereConvert.entityToVo(field);
    }

    @Override
    public ControlAtmosphereFieldVo getLatestControlByRoomId(Long roomId) {
        Optional<ControlAtmosphereField> atmOptional = atmosphereControlRepo.getLatestControlByRoomId(roomId);
        return atmOptional.map(ControlAtmosphereConvert::entityToVo).orElse(null);
    }

    @Override
    public void stopAtmControl(Long id) {
        atmosphereControlRepo.stopAtmControl(id);
    }

    @Override
    public List<ControlAtmosphereTemplateVo> findTemplateListByRoomId(Long roomId) {
        List<ControlAtmosphereTemplate> templates = atmosphereTemplateRepo.findTemplateListByRoomId(roomId);
        return CommonConvert.map(templates, ControlAtmosphereTemplateConvert::entityToVo);
    }

    @Override
    public void removeTemplate(Long id) {
        atmosphereTemplateRepo.removeTemplate(id);
    }

    @Override
    public void saveTemplate(Long orgId, ControlAtmosphereTemplateRo ro) {
        ControlAtmosphereTemplate template = ControlAtmosphereTemplateConvert.roToEntity(ro);
        assert template != null;
        template.setOrgId(orgId);
        atmosphereTemplateRepo.saveTemplate(template);
    }


}
