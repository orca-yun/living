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
public class FilePartInfo extends BaseEntity {
    private Long id;

    private String fileId;

    private String fileName;

    private String bucket;

    private String pathName;

    private Integer partNum;

    private Long partSize;

    private Long partCrc32;

    private String etag;

    private Integer status;
}
