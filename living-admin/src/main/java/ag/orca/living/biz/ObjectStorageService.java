package ag.orca.living.biz;


import ag.orca.living.common.CommonConvert;
import ag.orca.living.config.ObjectStorageConfig;
import ag.orca.living.errors.OrcaException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

@Service
public class ObjectStorageService {

    @Resource
    AmazonS3 amazonS3;

    @Resource
    ObjectStorageConfig objectStorageConfig;


    public String videoBucket() {
        return objectStorageConfig.getVideoBucket();
    }

    public String fileBucket() {
        return objectStorageConfig.getFileBucket();
    }

    public String imageBucket() {
        return objectStorageConfig.getDefaultBucket();
    }


    public String imgPath(String bucket, String key) {
        return objectStorageConfig.endpoint(bucket) + PATH_SPLIT + key;
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
            objectRequest
                    .withStorageClass(StorageClass.Standard)
                    .withCannedAcl(controlList);
            PutObjectResult result = amazonS3.putObject(objectRequest);
        } catch (IOException e) {
            throw OrcaException.error(String.format("上传文件失败, 原因：%s", e.getMessage()));
        }
    }


    /**
     * 初始化分片上传
     *
     * @param bucket
     * @param key
     * @param dataSize
     * @param partSize
     * @param controlList
     * @return
     */
    public String initiateMultipartUpload(String bucket,
                                          String key,
                                          Long dataSize,
                                          Long partSize,
                                          CannedAccessControlList controlList) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucket, key);
        request.withStorageClass(StorageClass.Standard)
                .withCannedACL(controlList);
        try {
            InitiateMultipartUploadResult result = amazonS3.initiateMultipartUpload(request);
            return result.getUploadId();
        } catch (Throwable e) {
            throw OrcaException.error(String.format("上传文件失败, 原因：%s", e.getMessage()));
        }
    }

    /**
     * 分片上传
     *
     * @param bucket
     * @param key
     * @param uploadId
     * @param partNum
     * @param file
     * @return
     */
    public Pair<Integer, String> uploadPart(String bucket,
                                            String key,
                                            String uploadId,
                                            Integer partNum,
                                            MultipartFile file) {
        UploadPartRequest request = new UploadPartRequest();
        request.withBucketName(bucket)
                .withKey(key)
                .withUploadId(uploadId);
        try {
            request.withPartNumber(partNum)
                    .withPartSize(file.getSize())
                    .withInputStream(file.getInputStream());
            UploadPartResult result = amazonS3.uploadPart(request);
            return Pair.of(result.getPartNumber(), result.getETag());
        } catch (Throwable e) {
            throw OrcaException.error(String.format("上传文件失败, 原因：%s", e.getMessage()));
        }
    }


    /**
     * 完成分片上传
     *
     * @param bucket
     * @param key
     * @param uploadId
     * @param eTags
     */
    public String completeMultipartUpload(String bucket,
                                          String key,
                                          String uploadId,
                                          List<Pair<Integer, String>> eTags) {
        CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest();
        request.withBucketName(bucket)
                .withKey(key)
                .withUploadId(uploadId);
        List<PartETag> partETags = CommonConvert.map(eTags, s -> new PartETag(s.getLeft(), s.getRight()));
        request.withPartETags(partETags);
        try {
            CompleteMultipartUploadResult result = amazonS3.completeMultipartUpload(request);
            return result.getETag();
        } catch (Throwable e) {
            throw OrcaException.error(String.format("上传文件失败, 原因：%s", e.getMessage()));
        }
    }

    public void abortMultipartUpload(String bucket,
                                     String key,
                                     String uploadId) {
        AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucket, key, uploadId);
        try {
            amazonS3.abortMultipartUpload(request);
        } catch (Throwable e) {
            throw OrcaException.error(String.format("上传文件失败, 原因：%s", e.getMessage()));
        }
    }


}
