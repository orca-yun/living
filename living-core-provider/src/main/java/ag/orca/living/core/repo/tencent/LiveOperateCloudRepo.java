package ag.orca.living.core.repo.tencent;

import ag.orca.living.config.LivingConfig;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamOnlineListRequest;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamOnlineListResponse;
import com.tencentcloudapi.live.v20180801.models.DropLiveStreamRequest;
import com.tencentcloudapi.live.v20180801.models.DropLiveStreamResponse;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Slf4j
@Repository
public class LiveOperateCloudRepo {

    @Resource
    LivingConfig livingConfig;


    @SneakyThrows
    public void queryAndDropLiveStream(String streamName) {
        String domain = livingConfig.getPushDomain();
        String region = livingConfig.getRegion();
        String appName = livingConfig.getAppName();

        LiveClient client = new LiveClient(new Credential(livingConfig.getSecretId(), livingConfig.getSecretKey()), region);
        DescribeLiveStreamOnlineListRequest queryRequest = new DescribeLiveStreamOnlineListRequest();
        queryRequest.setAppName(appName);
        queryRequest.setStreamName(streamName);
        queryRequest.setDomainName(domain);
        DescribeLiveStreamOnlineListResponse response = client.DescribeLiveStreamOnlineList(queryRequest);
        if (response.getTotalNum() == 1) {
            DropLiveStreamRequest request = new DropLiveStreamRequest();
            request.setAppName(appName);
            request.setStreamName(streamName);
            request.setDomainName(domain);
            DropLiveStreamResponse dropResponse = client.DropLiveStream(request);
            if (Objects.nonNull(dropResponse)) {
                log.info("=====》断开直播流成功 {} {}", request.getDomainName(), request.getStreamName());
            } else {
                log.info("=====》断开直播流失败 {} {}", request.getDomainName(), request.getStreamName());
            }
        }
    }

}
