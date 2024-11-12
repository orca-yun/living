package ag.orca.living.config;

import ag.orca.living.biz.TokenService;
import ag.orca.living.core.auth.AdminAuth;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.interceptors.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 先用拦截器去拦截，后面迁移到网关层。应用层面不做鉴权操作
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    JwtConfig jwtConfig;

    @Resource
    TokenService tokenService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(
                        jwtConfig.getHeaderString(),
                        s -> tokenService.checkToken(s),
                        s -> {
                            AdminAuth auth = tokenService.parseToken(s);
                            return new AuthInfo(auth);
                        }, null))
                .excludePathPatterns(
                        "/", "/health", "/favicon.ico",
                        "/api-docs", "/api-docs/**",
                        "/swagger-ui/**", "/swagger-ui.html",
                        "/v1/admin/login"
                )
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}