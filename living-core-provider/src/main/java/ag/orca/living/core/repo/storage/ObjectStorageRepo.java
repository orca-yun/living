package ag.orca.living.core.repo.storage;

import ag.orca.living.config.ObjectStorageConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

@Slf4j
@Repository
public class ObjectStorageRepo {

    @Resource
    ObjectStorageConfig objectStorageConfig;

    public String imgPath(String bucket, String key) {
        return objectStorageConfig.endpoint(bucket) + PATH_SPLIT + key;
    }

    public String imageBucket() {
        return objectStorageConfig.getDefaultBucket();
    }
}
