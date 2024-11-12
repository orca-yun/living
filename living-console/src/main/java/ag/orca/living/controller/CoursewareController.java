package ag.orca.living.controller;

import ag.orca.living.api.stream.LivingCoursewareService;
import ag.orca.living.biz.ObjectStorageService;
import ag.orca.living.common.FileMimeEnum;
import ag.orca.living.common.ObjectStorageWrapper;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;
import ag.orca.living.util.I18nUtil;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/courseware")
@Tag(name = "课件管理")
public class CoursewareController extends AbstractConsoleController {

    @Resource
    ObjectStorageService objectStorageService;

    @DubboReference
    LivingCoursewareService coursewareService;

    @Operation(summary = "获取课件列表")
    @GetMapping("/{roomId}")
    public OrcaResult<List<LivingCoursewareVo>> courseware(@PathVariable("roomId") Long roomId) {
        checkRoom(roomId);
        List<LivingCoursewareVo> vos = coursewareService.findListByRoomId(roomId);
        return OrcaResult.success(vos);
    }

    @Operation(summary = "课件删除")
    @DeleteMapping("/{roomId}")
    public OrcaResult<Void> removeCourseware(@PathVariable("roomId") Long roomId,
                                             @NotNull(message = "课件ID不能为空")
                                             @RequestParam("id") Long id) {
        checkRoom(roomId);
        coursewareService.removeCourseware(id);
        return OrcaResult.success();
    }

    @Operation(summary = "课件上传转换")
    @PostMapping("/{roomId}/convert")
    public OrcaResult<LivingCoursewareVo> convert(@PathVariable("roomId") Long roomId,
                                                  @NotNull(message = "课件文件不能为空")
                                                  @RequestParam("file") MultipartFile file) {
        checkRoom(roomId);
        String contentType = file.getContentType();
        Optional<FileMimeEnum> fileMime = FileMimeEnum.ofPdf(contentType);
        OrcaAssert.match(fileMime.isEmpty(), I18nUtil.getMessage("courseware.mime.type.invalid"));
        String fileName = file.getOriginalFilename();
        String realFileName = ObjectStorageWrapper.buildRealFileName(fileMime.get());
        String key = ObjectStorageWrapper.buildPathName(realFileName);
        String fileBucket = objectStorageService.fileBucket();
        objectStorageService.putObject(file, fileBucket, key, CannedAccessControlList.Private);
        LivingCoursewareVo vo = coursewareService.triggerConvertPdfToImage(roomId, fileName, fileBucket, key);
        return OrcaResult.success(vo);
    }
}
