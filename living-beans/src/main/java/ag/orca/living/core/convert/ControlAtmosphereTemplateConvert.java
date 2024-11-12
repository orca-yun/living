package ag.orca.living.core.convert;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.control.ControlAtmosphereTemplate;
import ag.orca.living.core.ro.control.ControlAtmosphereTemplateRo;
import ag.orca.living.core.vo.control.ControlAtmosphereTemplateVo;
import ag.orca.living.util.JsonUtil;

import java.util.List;
import java.util.Objects;

public class ControlAtmosphereTemplateConvert {

    public static ControlAtmosphereTemplateVo entityToVo(ControlAtmosphereTemplate template) {
        if (Objects.isNull(template)) {
            return null;
        }
        List<String> text = JsonUtil.jsonToBean(template.getTextContent(), List.class);
        List<Integer> gifts = JsonUtil.jsonToBean(template.getGiftContent(), List.class);
        List<Long> giftLongs = CommonConvert.map(gifts, Integer::longValue);
        return ControlAtmosphereTemplateVo.builder()
                .id(template.getId())
                .orgId(template.getOrgId())
                .roomId(template.getRoomId())
                .textContent(text)
                .giftContent(giftLongs)
                .build();
    }

    public static ControlAtmosphereTemplate roToEntity(ControlAtmosphereTemplateRo ro) {
        if (Objects.isNull(ro)) {
            return null;
        }
        String text = JsonUtil.beanToJson(ro.getTextContentList());
        String gift = JsonUtil.beanToJson(ro.getGiftIdList());

        return ControlAtmosphereTemplate.builder()
                .id(ro.getId())
                .roomId(ro.getRoomId())
                .textContent(text)
                .giftContent(gift)
                .build();
    }
}
