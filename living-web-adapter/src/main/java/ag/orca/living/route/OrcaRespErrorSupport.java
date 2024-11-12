package ag.orca.living.route;

import ag.orca.living.common.OrcaResult;
import ag.orca.living.util.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public interface OrcaRespErrorSupport {
    @SneakyThrows
    default void responseError(HttpServletResponse response, HttpStatus status, String msg) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(JsonUtil.beanToJson(OrcaResult.fail(status.value(), msg)));
    }
}
