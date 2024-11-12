package ag.orca.living.controller;


import ag.orca.living.biz.ObjectStorageService;
import ag.orca.living.common.FileMimeEnum;
import ag.orca.living.common.ObjectStorageWrapper;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.util.I18nUtil;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequestMapping("/v2/upload")
@Tag(name = "上传文件管理")
public class UploadController extends AbstractConsoleController {

    @Resource
    ObjectStorageService objectStorageService;


    @Operation(summary = "上传图片")
    @PostMapping
    public OrcaResult<String> upload(@NotNull(message = "文件不能为空")
                                     @RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        Optional<FileMimeEnum> fileMime = FileMimeEnum.ofImage(contentType);
        OrcaAssert.match(fileMime.isEmpty(), I18nUtil.getMessage("upload.image.invalid"));
        String fileName = ObjectStorageWrapper.buildRealFileName(fileMime.get());
        String bucket = objectStorageService.imageBucket();
        String key = ObjectStorageWrapper.buildPathName(fileName);
        objectStorageService.putObject(file, bucket, key, CannedAccessControlList.PublicRead);
        String path = objectStorageService.imgPath(bucket, key);
        return OrcaResult.success(path);
    }


}
