package ag.orca.living.core.dao.filepart;

import ag.orca.living.core.entity.filepart.FilePartInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilePartInfoMapper {
    int removeByFileId(@Param("fileId") String fileId);

    List<FilePartInfo> findFilePartInfoListByFileId(@Param("fileId") String fileId);

    int insert(FilePartInfo info);
}
