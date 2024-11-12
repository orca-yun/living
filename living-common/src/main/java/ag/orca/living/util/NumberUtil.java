package ag.orca.living.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ag.orca.living.common.OrcaConst._100;

public class NumberUtil {
    public static Double toDouble(Long val) {
        return BigDecimal.valueOf(val).divide(_100, 2, RoundingMode.HALF_UP).doubleValue();
    }
}
