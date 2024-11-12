package ag.orca.living.core.convert;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.control.ControlAtmosphereField;
import ag.orca.living.core.enums.ControlExecStatusEnum;
import ag.orca.living.core.ro.control.SendAtmosphereMessageRo;
import ag.orca.living.core.vo.control.ControlAtmosphereFieldVo;
import ag.orca.living.util.JsonUtil;

import java.util.List;
import java.util.Objects;

/**
 * 
 * 
 */
public class ControlAtmosphereConvert {

    public static ControlAtmosphereFieldVo entityToVo(ControlAtmosphereField entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        List<Integer> gifts = JsonUtil.jsonToBean(entity.getGiftContent(), List.class);
        List<Long> giftLongs = CommonConvert.map(gifts, Integer::longValue);
        return ControlAtmosphereFieldVo.builder()
                .id(entity.getId())
                .roomId(entity.getRoomId())
                .textContentList(JsonUtil.jsonToBean(entity.getTextContent(), List.class))
                .giftIdList(giftLongs)
                .quantity(entity.getQuantity())
                .textInterval(entity.getTextInterval())
                .giftInterval(entity.getGiftInterval())
                .execStatus(entity.getExecStatus())
                .expEndTime(entity.getExpEndTime())
                .build();
    }

    public static ControlAtmosphereField roToEntity(SendAtmosphereMessageRo ro, Long orgId) {
        return ControlAtmosphereField.builder()
                .orgId(orgId)
                .roomId(ro.getRoomId())
                .textContent(JsonUtil.beanToJson(ro.getTextContentList()))
                .giftContent(JsonUtil.beanToJson(ro.getGiftIdList()))
                .quantity(ro.getQuantity())
                .textInterval(ro.getTextInterval())
                .giftInterval(ro.getGiftInterval())
                .execStatus(ControlExecStatusEnum.STARTED.getCode())
                .build();
    }
}
