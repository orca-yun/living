package ag.orca.living.core.convert;

import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.entity.filepart.FilePartInfo;
import ag.orca.living.core.enums.FilePartStatusEnum;
import ag.orca.living.core.ro.filepart.InitFilePartMultipartRo;
import ag.orca.living.core.ro.filepart.RunFilePartMultipartRo;
import ag.orca.living.core.vo.filepart.FilePartInitResultVo;

import java.util.Objects;

public class FilePartConvert {

    public static FilePartInitResultVo buildInitResult(FileInfo fileInfo, Integer fastStatus) {
        return FilePartInitResultVo.builder()
                .fileId(fileInfo.getId())
                .fastStatus(fastStatus)
                .bucket(fileInfo.getBucket())
                .key(fileInfo.getPathName())
                .build();
    }

    public static FileInfo fileRoToEntity(InitFilePartMultipartRo ro,
                                          String bucket,
                                          String pathName,
                                          String uploadId) {
        return Objects.isNull(ro) ? null :
                FileInfo.builder()
                        .id(uploadId)
                        .fileName(ro.getFileName())
                        .realName(ro.getFileName())
                        .fileMd5(ro.getFileMd5())
                        .bucket(bucket)
                        .pathName(pathName)
                        .fileSize(ro.getFileSize())
                        .filePart(ro.getFilePart())
                        .fileCrc32(ro.getFileCrc32())
                        .status(FilePartStatusEnum.INIT.getCode())
                        .build();
    }

    public static FilePartInfo filePartRoToEntity(RunFilePartMultipartRo ro,
                                                  FileInfo fileInfo,
                                                  Integer partNum,
                                                  String etag) {


        return Objects.isNull(ro) ? null :
                FilePartInfo.builder()
                        .fileId(fileInfo.getId())
                        .fileName(ro.getFile().getName())
                        .bucket(fileInfo.getBucket())
                        .pathName(fileInfo.getPathName())
                        .partNum(partNum)
                        .etag(etag)
                        .partSize(ro.getFile().getSize())
                        .status(FilePartStatusEnum.NORMAL.getCode())
                        .build();
    }

}
