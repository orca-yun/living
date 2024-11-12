package ag.orca.living.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public class OrcaWebUtils {

    public static String getHeaderValue(String headerName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes) && !StringUtils.isEmpty(headerName)) {
            return attributes.getRequest().getHeader(headerName);
        }
        return null;
    }

}
