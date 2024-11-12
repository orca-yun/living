package ag.orca.living.controller;

import ag.orca.living.api.examine.ImExamineService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.enums.ExamineStatusEnum;
import ag.orca.living.core.ro.examine.ImExamineRecordRo;
import ag.orca.living.core.ro.query.QueryImExamineRecordRo;
import ag.orca.living.core.vo.examine.ImExamineRecordVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ag.orca.living.common.OrcaConst.IM_EXAMINE_QUERY_MAX_SIZE;

/**
 * 待审核列表
 */
@RestController
@RequestMapping("/v2/examine")
@Tag(name = "审核相关")
public class ExamineController extends AbstractConsoleController {


    @DubboReference
    ImExamineService imExamineService;

    @Operation(summary = "待审核消息在线列表")
    @GetMapping("/msg/{roomId}")
    public OrcaResult<List<ImExamineRecordVo>> todoExamineMsgList(@PathVariable("roomId") Long roomId,
                                                                  QueryImExamineRecordRo ro) {
        int size = (ro.getSize() <= 0 || ro.getSize() > IM_EXAMINE_QUERY_MAX_SIZE) ? IM_EXAMINE_QUERY_MAX_SIZE : ro.getSize();
        checkRoom(roomId);
        return OrcaResult.success(imExamineService.findLatestExamineMsgList(roomId, size, ExamineStatusEnum.init, ro.getKeyword()));
    }


    @Operation(summary = "操作审核")
    @PostMapping("/msg/{roomId}")
    public OrcaResult<Void> operateImExamineRecord(@PathVariable("roomId") Long roomId,
                                                   @Validated @RequestBody ImExamineRecordRo ro) {
        checkRoom(roomId);
        ro.setOperateUid(getUid());
        ro.setOperateNickname(getNickname());
        imExamineService.operateImExamineRecord(roomId, ro);
        return OrcaResult.success();
    }


}
