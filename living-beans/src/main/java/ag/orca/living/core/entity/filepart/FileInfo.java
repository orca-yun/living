package ag.orca.living.core.entity.filepart;

import ag.orca.living.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
public class FileInfo extends BaseEntity {

    private String id;

    private String fileName;

    private String realName;

    private String bucket;

    private String pathName;

    private Long fileSize;

    private Long filePart;

    private String fileMd5;

    private Long fileCrc32;

    private Integer status;


}
