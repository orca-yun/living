package ag.orca.living.interceptors;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.route.OrcaRespErrorSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * 先用拦截器去拦截，后面迁移到网关层。应用层面不做鉴权操作
 */

@AllArgsConstructor
@Data
public class TokenInterceptor implements HandlerInterceptor, OrcaRespErrorSupport {

    private String authorizationName;

    private Predicate<String> predicate;

    private Function<String, AuthInfo> func;

    private Predicate<AuthInfo> blackPredicate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(authorizationName);
        try {
            if (!predicate.test(token)) {
                responseError(response, HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
                return false;
            }
            AuthInfo info = func.apply(token);
            if (Objects.isNull(info)) {
                responseError(response, HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
                return false;
            }
            if (Objects.nonNull(blackPredicate) && blackPredicate.test(info)) {
                responseError(response, HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase());
                return false;
            }
            AuthInfoThreadLocal.put(info);
            return true;
        } catch (Throwable e) {
            responseError(response, HttpStatus.UNAUTHORIZED, e.getMessage());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthInfoThreadLocal.clear();
    }

}
