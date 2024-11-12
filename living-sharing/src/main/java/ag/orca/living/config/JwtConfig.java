package ag.orca.living.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig {
    private String secret;
    private Long expirationTime;
    private String tokenPrefix;
    private String headerString;
}
