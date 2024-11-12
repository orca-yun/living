package ag.orca.living.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxConfig {

    private String oauth2Format;

    private String callBack;

    private String appid;

    private String secret;
}
