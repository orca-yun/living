package ag.orca.living.config;

import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.biz.TokenService;
import ag.orca.living.core.auth.CrossAuth;
import ag.orca.living.interceptors.DelayTokenInterceptor;
import ag.orca.living.interceptors.TokenInterceptor;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static ag.orca.living.common.OrcaConst.DELAY_DAYS;

/**
 * 先用拦截器去拦截，后面迁移到网关层。应用层面不做鉴权操作
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    JwtConfig jwtConfig;

    @Resource
    TokenService tokenService;

    @DubboReference
    TokenOperateService tokenOperateService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] exclude = {
                "/", "/health", "/favicon.ico",
                "/api-docs", "/api-docs/**",
                "/swagger-ui/**", "/swagger-ui.html",
                "/v2/cross/login"
        };

        registry.addInterceptor(
                        new TokenInterceptor(jwtConfig.getHeaderString(),
                                s -> tokenService.checkToken(s),
                                s -> {
                                    CrossAuth auth = tokenService.parseToken(s);
                                    return tokenOperateService.getAuthInfoByCrossAuth(auth).orElse(null);
                                }, null))
                .excludePathPatterns(exclude)
                .order(Ordered.LOWEST_PRECEDENCE - 1);

        registry.addInterceptor(new DelayTokenInterceptor(s -> tokenOperateService.delayAuth(s, DELAY_DAYS)))
                .excludePathPatterns(exclude)
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}
