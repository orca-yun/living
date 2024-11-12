package ag.orca.living.config;

import ag.orca.living.api.interact.InteractBlacklistService;
import ag.orca.living.api.token.TokenOperateService;
import ag.orca.living.biz.TokenService;
import ag.orca.living.core.auth.ShareAuth;
import ag.orca.living.interceptors.TokenInterceptor;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @DubboReference
    TokenOperateService tokenOperateService;


    @DubboReference
    InteractBlacklistService blacklistService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                        new TokenInterceptor(
                                jwtConfig.getHeaderString(),
                                s -> tokenService.checkToken(s),
                                s -> {
                                    ShareAuth auth = tokenService.parseToken(s);
                                    return tokenOperateService.getAuthInfoByShareAuth(auth).orElse(null);
                                },
                                authInfo -> blacklistService.isInBlacklist(authInfo.getRoomId(), authInfo.getUid())))
                .excludePathPatterns(
                        "/", "/health", "/favicon.ico",
                        "/api-docs", "/api-docs/**",
                        "/swagger-ui/**", "/swagger-ui.html",

                        "/v3/share/live/**",
                        "/v3/share/prepare/check",

                        "/v3/auth/wx/qrcode",
                        "/v3/auth/wx/e2e/**",

                        "/v3/tl/pay/notify/**"
                )
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}
