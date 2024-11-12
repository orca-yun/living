package ag.orca.living.core.repo.control;

import ag.orca.living.core.dao.control.ControlAtmosphereTemplateMapper;
import ag.orca.living.core.entity.control.ControlAtmosphereTemplate;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Repository
public class AtmosphereTemplateRepo {

    @Resource
    ControlAtmosphereTemplateMapper controlAtmosphereTemplateMapper;


    @Transactional(rollbackFor = Exception.class)
    public void saveTemplate(ControlAtmosphereTemplate template) {
        if (Objects.isNull(template.getId())) {
            controlAtmosphereTemplateMapper.insertSelective(template);
        } else {
            controlAtmosphereTemplateMapper.updateByPrimaryKeySelective(template);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeTemplate(Long id) {
        controlAtmosphereTemplateMapper.logicDelById(id, LocalDateTime.now());
    }

    public List<ControlAtmosphereTemplate> findTemplateListByRoomId(Long roomId) {
        return controlAtmosphereTemplateMapper.findListByRoomId(roomId);
    }
}
