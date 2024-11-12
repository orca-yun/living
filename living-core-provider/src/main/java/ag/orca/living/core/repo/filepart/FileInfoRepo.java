package ag.orca.living.core.repo.filepart;

import ag.orca.living.core.dao.filepart.FileInfoMapper;
import ag.orca.living.core.dao.filepart.FilePartInfoMapper;
import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.entity.filepart.FilePartInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class FileInfoRepo {

    @Resource
    FileInfoMapper fileInfoMapper;

    @Resource
    FilePartInfoMapper filePartInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void saveFileInfo(FileInfo fileInfo) {
        fileInfoMapper.insert(fileInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeByFileId(String fileId) {
        fileInfoMapper.removeById(fileId);
        filePartInfoMapper.removeByFileId(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void completeFileInfoById(String id) {
        fileInfoMapper.completeById(id, LocalDateTime.now());
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveFilePartInfo(FilePartInfo info) {
        filePartInfoMapper.insert(info);
    }


    public List<FilePartInfo> findFilePartInfoListByFileId(String fileId) {
        return filePartInfoMapper.findFilePartInfoListByFileId(fileId);
    }


    public FileInfo findFileInfoById(String id) {
        return fileInfoMapper.findById(id);
    }

    public FileInfo findFileInfoByMd5AndCrc32(String md5, Long crc32) {
        return fileInfoMapper.findByMd5AndCrc32(md5, crc32);
    }


}
