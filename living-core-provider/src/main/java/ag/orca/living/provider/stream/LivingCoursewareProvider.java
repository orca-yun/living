package ag.orca.living.provider.stream;

import ag.orca.living.api.stream.LivingCoursewareService;
import ag.orca.living.core.convert.LivingCoursewareConvert;
import ag.orca.living.core.entity.stream.LivingCourseware;
import ag.orca.living.core.repo.stream.LivingCoursewareRepo;
import ag.orca.living.core.repo.stream.manage.LivingCoursewareManage;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@Slf4j
@DubboService
public class LivingCoursewareProvider implements LivingCoursewareService {

    @Resource
    LivingCoursewareRepo coursewareRepo;

    @Resource
    LivingCoursewareManage coursewareManage;

    @Override
    public LivingCoursewareVo triggerConvertPdfToImage(Long roomId, String fileName, String bucket, String key) {
        LivingCourseware pair = coursewareRepo.saveConvertPdfToImageRecord(roomId, fileName, bucket, key);
        return LivingCoursewareConvert.entityToVo(pair);
    }

    @Override
    public List<LivingCoursewareVo> findListByRoomId(Long roomId) {
        return coursewareManage.findCoursewareListByRoomId(roomId);
    }

    @Override
    public void removeCourseware(Long id) {
        coursewareRepo.logicRemoveCoursewareById(id);
    }
}
