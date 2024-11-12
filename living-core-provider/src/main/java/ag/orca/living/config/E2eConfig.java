package ag.orca.living.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "e2e")
public class E2eConfig {

    private String adminFormat;

    private String anchorFormat;

    private String assistantFormat;

    // 观看端 直播时刻  直播观看地址
    private String shareLiveFormat;

    // 观看端非直播时刻的 回放直播地址
    private String sharePlayFormat;

    private String pcRedirectFormat;

    private String wxRedirectFormat;


}
