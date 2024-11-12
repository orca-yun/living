package ag.orca.living.controller;

import ag.orca.living.biz.FilePartService;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.ro.filepart.CancelFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.CompleteFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.InitFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.RunFilePartMultipartRo;
import ag.orca.living.core.vo.filepart.FilePartInitResultVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/upload/part")
@Tag(name = "分片上传文件管理")
public class UploadFilePartController extends AbstractAdminController {

    @Resource
    FilePartService filePartService;

    @Operation(summary = "初始化分片上传")
    @PostMapping("/init")
    public OrcaResult<FilePartInitResultVo> initPart(@Validated @RequestBody InitFilePartMultipartRo ro) {
        FilePartInitResultVo result = filePartService.initPart(ro);
        return OrcaResult.success(result);
    }

    @Operation(summary = "分片上传")
    @PostMapping("/transport")
    public OrcaResult<Void> uploadPart(RunFilePartMultipartRo ro) {
        filePartService.uploadPart(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "完成上传")
    @PostMapping("/complete")
    public OrcaResult<Void> completePart(@Validated @RequestBody CompleteFilePartMultipartRo ro) {
        filePartService.completePart(ro);
        return OrcaResult.success();
    }

    @Operation(summary = "放弃上传")
    @PostMapping("/cancel")
    public OrcaResult<Void> cancelPart(@Validated @RequestBody CancelFilePartMultipartRo ro) {
        filePartService.cancelPart(ro);
        return OrcaResult.success();
    }

}
