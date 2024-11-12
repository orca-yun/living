package ag.orca.living.controller;

import ag.orca.living.api.base.OrgRobotService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.org.OrgBatchRobotRo;
import ag.orca.living.core.ro.org.OrgRobotRo;
import ag.orca.living.core.ro.query.QueryRobotRo;
import ag.orca.living.core.vo.org.OrgRobotVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/org/robot")
@Tag(name = "机构机器人库")
public class OrgRobotController extends AbstractAdminController {
    @DubboReference
    OrgRobotService robotService;

    @Operation(summary = "机器人列表")
    @GetMapping
    public OrcaPageResult<OrgRobotVo> robotList(QueryRobotRo ro) {
        Long orgId = getOrgId();
        Pair<List<OrgRobotVo>, Long> pair = robotService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "保存机器人")
    @PostMapping
    public OrcaResult<Void> saveRobot(@Validated @RequestBody OrgBatchRobotRo ro) {
        robotService.batchSave(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "编辑机器人")
    @PutMapping
    public OrcaResult<Void> editRobot(@Validated @RequestBody OrgRobotRo ro) {
        robotService.edit(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "删除机器人")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeRobot(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        robotService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }
}
