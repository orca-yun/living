package ag.orca.living.provider.examine;

import ag.orca.living.api.examine.ImExamineService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.ImExamineConvert;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.mongo.ImExamineRecord;
import ag.orca.living.core.repo.examine.ImExamineRepo;
import ag.orca.living.core.ro.examine.ImExamineRecordRo;
import ag.orca.living.core.vo.examine.ImExamineRecordVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Comparator;
import java.util.List;

import static ag.orca.living.common.OrcaConst.IM_EXAMINE_QUERY_MAX_SIZE;


@Slf4j
@DubboService
public class ImExamineProvider implements ImExamineService {

    @Resource
    ImExamineRepo imExamineRepo;

    @Override
    public List<ImExamineRecordVo> findLatestExamineMsgList(Long roomId,
                                                            int size,
                                                            ExamineStatusEnum status,
                                                            String keyword) {
        size = (size <= 0 || size > IM_EXAMINE_QUERY_MAX_SIZE) ? IM_EXAMINE_QUERY_MAX_SIZE : size;
        List<ImExamineRecord> records = imExamineRepo.findLatestExamineMsgList(roomId, size, status, keyword);
        records = records.stream().sorted(Comparator.comparing(ImExamineRecord::getCts)).toList();
        return CommonConvert.map(records, ImExamineConvert::recordToVo);
    }

    @Override
    public void operateImExamineRecord(Long roomId, ImExamineRecordRo ro) {
        imExamineRepo.operateImExamineRecord(roomId, ro.getId(), ro.getExamineStatus(), ro.getOperateUid(), ro.getOperateNickname());
    }
}
