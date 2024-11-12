package ag.orca.living.provider.filepart;

import ag.orca.living.api.filepart.FileInfoService;
import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.entity.filepart.FilePartInfo;
import ag.orca.living.core.repo.filepart.FileInfoRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Optional;

@Slf4j
@DubboService
public class FilePartProvider implements FileInfoService {

    @Resource
    FileInfoRepo fileInfoRepo;

    @Override
    public Optional<FileInfo> findByMd5AndCrc32(String md5, Long crc32) {
        return Optional.ofNullable(fileInfoRepo.findFileInfoByMd5AndCrc32(md5, crc32));
    }

    @Override
    public Optional<FileInfo> findById(String id) {
        return Optional.ofNullable(fileInfoRepo.findFileInfoById(id));
    }

    @Override
    public void completeFileInfoById(String id) {
        fileInfoRepo.completeFileInfoById(id);
    }

    @Override
    public void saveFileInfo(FileInfo fileInfo) {
        fileInfoRepo.saveFileInfo(fileInfo);
    }

    @Override
    public void saveFilePartInfo(FilePartInfo partInfo) {
        fileInfoRepo.saveFilePartInfo(partInfo);
    }

    @Override
    public List<FilePartInfo> findFilePartListByFileId(String fileId) {
        return fileInfoRepo.findFilePartInfoListByFileId(fileId);
    }

    @Override
    public void removeFileInfoByFileId(String fileId) {
        fileInfoRepo.removeByFileId(fileId);
    }

}
