package ag.orca.living.api.examine;

import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.ro.examine.ImExamineRecordRo;
import ag.orca.living.core.vo.examine.ImExamineRecordVo;

import java.util.List;

public interface ImExamineService {
    List<ImExamineRecordVo> findLatestExamineMsgList(Long roomId, int size, ExamineStatusEnum status, String keyword);

    void operateImExamineRecord(Long roomId, ImExamineRecordRo ro);
}
