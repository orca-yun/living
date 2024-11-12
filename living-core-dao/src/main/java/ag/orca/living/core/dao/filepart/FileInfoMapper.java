package ag.orca.living.core.dao.filepart;

import ag.orca.living.core.entity.filepart.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface FileInfoMapper {

    int removeById(@Param("id") String id);

    int insert(FileInfo fileInfo);

    FileInfo findById(@Param("id") String id);

    FileInfo findByMd5AndCrc32(@Param("fileMd5") String fileMd5, @Param("fileCrc32") Long fileCrc32);

    int completeById(@Param("id") String id, @Param("dateTime") LocalDateTime dateTime);
}
