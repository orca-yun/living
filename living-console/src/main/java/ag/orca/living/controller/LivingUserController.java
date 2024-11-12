package ag.orca.living.controller;

import ag.orca.living.api.share.LivingShareUserViewRecordService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.core.ro.query.QueryShareUserRo;
import ag.orca.living.core.vo.share.LivingShareUserViewRecordVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v2/user")
@Tag(name = "数据服务")
public class LivingUserController extends AbstractConsoleController {

    @DubboReference
    LivingShareUserViewRecordService livingShareUserViewRecordService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/records/{roomId}")
    public OrcaPageResult<LivingShareUserViewRecordVo> userList(@PathVariable("roomId") Long roomId,
                                                                QueryShareUserRo ro) {
        checkRoom(roomId);
        Long orgId = getOrgId();
        Pair<List<LivingShareUserViewRecordVo>, Long> pair = livingShareUserViewRecordService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }
}
