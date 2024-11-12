package ag.orca.living.common;

import ag.orca.living.errors.ErrorCodes;
import ag.orca.living.errors.OrcaException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Objects;


public class OrcaAssert {

    private static final int DEF_ERR_CODE = ErrorCodes.PARAM_ERR.getCode();

    public static void match(boolean m, String errMsg) {
        match(m, DEF_ERR_CODE, errMsg);
    }

    public static void match(boolean m, int code, String errMsg) {
        if (m) {
            throw OrcaException.error(code, errMsg);
        }
    }

    public static void isNull(Object object, String errMsg) {
        isNull(object, DEF_ERR_CODE, errMsg);
    }

    public static void isNull(Object object, int code, String errMsg) {
        if (Objects.isNull(object)) {
            throw OrcaException.error(code, errMsg);
        }
    }

    public static void isEmpty(Collection<?> collection, String errMsg) {
        isNull(collection, DEF_ERR_CODE, errMsg);
    }

    public static void isEmpty(Collection<?> collection, int code, String errMsg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw OrcaException.error(code, errMsg);
        }
    }

}
