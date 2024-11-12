package ag.orca.living.api.filepart;

import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.entity.filepart.FilePartInfo;

import java.util.List;
import java.util.Optional;

public interface FileInfoService {


    Optional<FileInfo> findByMd5AndCrc32(String fileMd5, Long fileCrc32);

    Optional<FileInfo> findById(String id);

    void completeFileInfoById(String id);


    void saveFileInfo(FileInfo fileInfo);


    void saveFilePartInfo(FilePartInfo build);

    List<FilePartInfo> findFilePartListByFileId(String fileId);

    void removeFileInfoByFileId(String fileId);


}
