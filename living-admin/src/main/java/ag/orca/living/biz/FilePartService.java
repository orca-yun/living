package ag.orca.living.biz;

import ag.orca.living.api.filepart.FileInfoService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.FileMimeEnum;
import ag.orca.living.common.ObjectStorageWrapper;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.FilePartConvert;
import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.entity.filepart.FilePartInfo;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.ro.filepart.CancelFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.CompleteFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.InitFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.RunFilePartMultipartRo;
import ag.orca.living.core.vo.filepart.FilePartInitResultVo;
import ag.orca.living.util.I18nUtil;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ag.orca.living.common.OrcaConst.DOT;

@Service
public class FilePartService {


    @DubboReference
    FileInfoService fileInfoService;

    @Resource
    ObjectStorageService objectStorageService;


    public FilePartInitResultVo initPart(InitFilePartMultipartRo ro) {
        if (Objects.nonNull(ro.getFileMd5()) && Objects.nonNull(ro.getFileCrc32())) {
            Optional<FileInfo> optional = fileInfoService.findByMd5AndCrc32(ro.getFileMd5(), ro.getFileCrc32());
            if (optional.isPresent()) {
                FileInfo f = optional.get();
                return FilePartConvert.buildInitResult(f, BoolEnum.TRUE.getCode());
            }
        }
        String fileName = ro.getFileName();
        String suffix = fileName.substring(fileName.lastIndexOf(DOT));
        Optional<FileMimeEnum> fileMime = FileMimeEnum.ofVideoWithSuffix(suffix);
        OrcaAssert.match(fileMime.isEmpty(), I18nUtil.getMessage("upload.video.invalid"));
        String videoBucket = objectStorageService.videoBucket();
        String realFileName = ObjectStorageWrapper.buildRealFileName(fileMime.get());
        String key = ObjectStorageWrapper.buildPathName(realFileName);
        String uploadId = objectStorageService.initiateMultipartUpload(videoBucket, key, ro.getFileSize(), ro.getFilePart(), CannedAccessControlList.Private);
        FileInfo fileInfo = FilePartConvert.fileRoToEntity(ro, videoBucket, key, uploadId);
        fileInfoService.saveFileInfo(fileInfo);
        assert fileInfo != null;
        return FilePartConvert.buildInitResult(fileInfo, BoolEnum.FALSE.getCode());
    }


    public Optional<FileInfo> checkAndGetFileInfo(String uploadId) {
        Optional<FileInfo> optional = fileInfoService.findById(uploadId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("upload.id.invalid"));
        return optional;
    }

    public void uploadPart(RunFilePartMultipartRo ro) {
        Optional<FileInfo> optional = checkAndGetFileInfo(ro.getFileId());
        optional.ifPresent(fileInfo -> {
            Pair<Integer, String> pair = objectStorageService.uploadPart(fileInfo.getBucket(),
                    fileInfo.getPathName(), fileInfo.getId(), ro.getPartNum(), ro.getFile());
            FilePartInfo partInfo = FilePartConvert.filePartRoToEntity(ro, fileInfo, pair.getLeft(), pair.getRight());
            fileInfoService.saveFilePartInfo(partInfo);
        });
    }

    public void completePart(CompleteFilePartMultipartRo ro) {
        Optional<FileInfo> optional = checkAndGetFileInfo(ro.getFileId());
        optional.ifPresent(fileInfo -> {
            List<FilePartInfo> partInfos = fileInfoService.findFilePartListByFileId(fileInfo.getId());
            List<Pair<Integer, String>> etags = CommonConvert.map(partInfos, s -> Pair.of(s.getPartNum(), s.getEtag()));
            objectStorageService.completeMultipartUpload(fileInfo.getBucket(), fileInfo.getPathName(), fileInfo.getId(), etags);
            fileInfoService.completeFileInfoById(fileInfo.getId());
        });
    }

    public void cancelPart(CancelFilePartMultipartRo ro) {
        Optional<FileInfo> optional = checkAndGetFileInfo(ro.getFileId());
        optional.ifPresent(fileInfo -> {
            String bucket = fileInfo.getBucket();
            String key = fileInfo.getPathName();
            objectStorageService.abortMultipartUpload(bucket, key, fileInfo.getId());
            fileInfoService.removeFileInfoByFileId(fileInfo.getId());
        });

    }
}
