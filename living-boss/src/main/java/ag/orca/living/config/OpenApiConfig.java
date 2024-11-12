package ag.orca.living.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "${springdoc.api-info.name}",
                version = "${springdoc.api-info.version}",
                description = "${springdoc.api-info.name} ${springdoc.api-info.version} 接口文档",
                contact = @Contact(name = "${springdoc.api-info.name}@orca.ag")
        ),
        security = @SecurityRequirement(name = "JWT")
)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
