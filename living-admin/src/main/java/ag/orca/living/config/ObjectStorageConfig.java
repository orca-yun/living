package ag.orca.living.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Data
@Configuration
@ConfigurationProperties(prefix = "s3")
public class ObjectStorageConfig {

    private String accessKey;

    private String secretKey;

    private String region;

    private String defaultBucket;

    private String videoBucket;

    private String fileBucket;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    public String endpoint(String bucket) {
        return MessageFormat.format("https://{0}.s3.{1}.amazonaws.com.cn", bucket, region);
    }

}
