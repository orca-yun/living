package ag.orca.living.biz;


import ag.orca.living.config.ObjectStorageConfig;
import ag.orca.living.errors.OrcaException;
import ag.orca.living.util.I18nUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

@Service
public class ObjectStorageService {

    @Resource
    AmazonS3 amazonS3;

    @Resource
    ObjectStorageConfig objectStorageConfig;


    public String fileBucket() {
        return objectStorageConfig.getFileBucket();
    }

    public String imageBucket() {
        return objectStorageConfig.getDefaultBucket();
    }

    public void putObject(MultipartFile file,
                          String bucket,
                          String key,
                          CannedAccessControlList controlList) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline");
        try {
            PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, file.getInputStream(), objectMetadata);
            objectRequest.withStorageClass(StorageClass.Standard)
                    .withCannedAcl(controlList);
            PutObjectResult result = amazonS3.putObject(objectRequest);
        } catch (IOException e) {
            throw OrcaException.error(String.format(I18nUtil.getMessage("upload.image.failed.reason"), e.getMessage()));
        }
    }

    public String imgPath(String bucket, String key) {
        return objectStorageConfig.endpoint(bucket) + PATH_SPLIT + key;
    }

}
