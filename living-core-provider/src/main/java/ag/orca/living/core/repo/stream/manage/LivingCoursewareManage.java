package ag.orca.living.core.repo.stream.manage;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.LivingCoursewareConvert;
import ag.orca.living.core.entity.stream.LivingCourseware;
import ag.orca.living.core.entity.stream.LivingCoursewareItem;
import ag.orca.living.core.repo.stream.LivingCoursewareRepo;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LivingCoursewareManage {

    @Resource
    LivingCoursewareRepo coursewareRepo;

    public List<LivingCoursewareVo> findCoursewareListByRoomId(Long roomId) {
        List<LivingCourseware> coursewares = coursewareRepo.findCoursewareListByRoomId(roomId);
        List<LivingCoursewareItem> items = coursewareRepo.findCoursewareItemListByRoomId(roomId);
        Map<Long, List<LivingCoursewareItem>> itemsGroup = items.stream()
                .collect(Collectors.groupingBy(LivingCoursewareItem::getCoursewareId));
        return CommonConvert.map(coursewares, k -> LivingCoursewareConvert.entityToVo(k, itemsGroup.getOrDefault(k.getId(), new ArrayList<>())));
    }
}
