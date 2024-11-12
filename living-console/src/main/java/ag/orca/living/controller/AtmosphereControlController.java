package ag.orca.living.controller;

import ag.orca.living.api.control.AtmosphereControlService;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.control.ControlAtmosphereTemplateRo;
import ag.orca.living.core.ro.control.SendAtmosphereMessageRo;
import ag.orca.living.core.vo.control.ControlAtmosphereFieldVo;
import ag.orca.living.core.vo.control.ControlAtmosphereTemplateVo;
import ag.orca.living.util.I18nUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @date 2024-05-02
 */
@Tag(name = "氛围场控")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/field/atmosphere")
public class AtmosphereControlController extends AbstractConsoleController {

    @DubboReference
    AtmosphereControlService atmosphereControlService;

    @Operation(summary = "发送直播间氛围场控消息")
    @PostMapping("/send")
    public OrcaResult<ControlAtmosphereFieldVo> sendAtmosphereMessage(@Validated @RequestBody SendAtmosphereMessageRo ro) {
        OrcaAssert.match(CollectionUtils.isNotEmpty(ro.getTextContentList()) && Objects.isNull(ro.getTextInterval()),
                I18nUtil.getMessage("atmosphere.text.content.interval.empty"));
        OrcaAssert.match(CollectionUtils.isNotEmpty(ro.getGiftIdList()) && Objects.isNull(ro.getGiftInterval()),
                I18nUtil.getMessage("atmosphere.gift.interval.empty"));
        return OrcaResult.success(atmosphereControlService.saveAtmosphereAndGeneMsg(ro));
    }

    @Operation(summary = "查询直播间氛围场控")
    @GetMapping("/{roomId}")
    public OrcaResult<ControlAtmosphereFieldVo> queryAtmosphereControl(
            @PathVariable("roomId") @Parameter(description = "直播间ID") Long roomId) {
        checkRoomId(roomId);
        checkRoom(roomId);
        return OrcaResult.success(atmosphereControlService.getLatestControlByRoomId(roomId));
    }

    @Operation(summary = "停止直播间氛围场控")
    @PostMapping("/stop/{id}")
    public OrcaResult<Void> stopAtmControl(
            @PathVariable("id") @Parameter(description = "氛围场控ID") Long id) {
        atmosphereControlService.stopAtmControl(id);
        return OrcaResult.success();
    }


    @Operation(summary = "氛围场控模板查询")
    @GetMapping("/template/{roomId}")
    public OrcaResult<List<ControlAtmosphereTemplateVo>> templates(
            @PathVariable("roomId") @Parameter(description = "房间ID") Long roomId) {
        return OrcaResult.success(atmosphereControlService.findTemplateListByRoomId(roomId));
    }

    @Operation(summary = "氛围场控模板保存")
    @DeleteMapping("/template/{roomId}")
    public OrcaResult<Void> saveTemplate(@PathVariable("roomId") @Parameter(description = "房间ID") Long roomId,
                                         @Validated @RequestBody ControlAtmosphereTemplateRo ro) {
        checkRoomId(roomId);
        checkRoom(roomId);
        Long orgId = getOrgId();
        atmosphereControlService.saveTemplate(orgId, ro);
        return OrcaResult.success();
    }

    @Operation(summary = "氛围场控模板删除")
    @DeleteMapping("/template/{id}")
    public OrcaResult<Void> removeTemplate(
            @PathVariable("id") @Parameter(description = "模板 ID") Long id) {
        atmosphereControlService.removeTemplate(id);
        return OrcaResult.success();
    }


}
