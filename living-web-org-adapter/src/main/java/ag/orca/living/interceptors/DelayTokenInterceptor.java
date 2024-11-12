package ag.orca.living.interceptors;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.route.OrcaRespErrorSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Data
public class DelayTokenInterceptor implements HandlerInterceptor, OrcaRespErrorSupport {

    private Consumer<AuthInfo> consumer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.nonNull(consumer)) {
            AuthInfo authInfo = AuthInfoThreadLocal.get();
            if (Objects.isNull(authInfo)) {
                responseError(response, HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
                return false;
            }
            consumer.accept(authInfo);
            log.info("HTTP 延迟 token {}", authInfo);
        }
        return true;
    }
}
