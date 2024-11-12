package ag.orca.living.controller;

import ag.orca.living.api.control.LivingRoomScriptService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.control.AddScriptRo;
import ag.orca.living.core.ro.control.BatchScriptRo;
import ag.orca.living.core.ro.control.UpdateScriptRo;
import ag.orca.living.core.ro.query.QueryScriptRo;
import ag.orca.living.core.vo.control.ControlScriptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "剧本场控")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/field")
public class ScriptControlController extends AbstractConsoleController {

    @DubboReference
    LivingRoomScriptService livingRoomScriptService;

    @Operation(summary = "添加直播间剧本")
    @PostMapping("/script")
    public OrcaResult<Void> addScript(@Validated @RequestBody AddScriptRo ro) {
        livingRoomScriptService.addScript(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "直播间剧本列表")
    @GetMapping("/script")
    public OrcaPageResult<ControlScriptVo> scriptList(QueryScriptRo ro) {
        Pair<List<ControlScriptVo>, Long> pair = livingRoomScriptService.findPageList(ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "保存直播间剧本并发送")
    @PostMapping("/script/to-send")
    public OrcaResult<Void> saveScriptAndSend(@Validated @RequestBody UpdateScriptRo ro) {
        livingRoomScriptService.saveScriptAndSend(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "保存直播间剧本并发送")
    @PostMapping("/script/save")
    public OrcaResult<Void> saveScript(@Validated @RequestBody UpdateScriptRo ro) {
        livingRoomScriptService.saveScript(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "批量发送")
    @PostMapping("/script/to-send/batch")
    public OrcaResult<Void> batchToSend(@Validated @RequestBody BatchScriptRo ro) {
        livingRoomScriptService.batchToSend(ro.getIds());
        return OrcaResult.success();
    }

    @Operation(summary = "批量删除")
    @PostMapping("/script/remove/batch")
    public OrcaResult<Void> batchRemove(@Validated @RequestBody BatchScriptRo ro) {
        livingRoomScriptService.remove(ro.getIds());
        return OrcaResult.success();
    }

    @Operation(summary = "删除直播间剧本")
    @DeleteMapping("/script/{id}")
    public OrcaResult<Void> removeScript(
            @PathVariable("id") @Parameter(description = "剧本ID") Long id) {
        livingRoomScriptService.remove(Collections.singletonList(id));
        return OrcaResult.success();
    }
}
