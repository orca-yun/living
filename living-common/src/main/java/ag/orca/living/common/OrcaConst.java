package ag.orca.living.common;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OrcaConst {
    public static final String SPLIT = "|";

    public static final String SPLIT_REGEX = "\\|";


    public static final String PATH_SPLIT = "/";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String MONTH_FORMAT = "yyyyMM";

    public static final String FULL_TIME_FORMAT = "yyyyMMddHHmmss";

    public static final String DEF_ORDER_TIME_FORMAT = "yyMMddHHmmss";

    public static final DateTimeFormatter FULL_TIME_FORMATTER = DateTimeFormatter.ofPattern(FULL_TIME_FORMAT);
    public static final DateTimeFormatter DEF_ORDER_FORMATTER = DateTimeFormatter.ofPattern(DEF_ORDER_TIME_FORMAT);


    public static final BigDecimal _100 = BigDecimal.valueOf(100);

    public static final int DOT = '.';

    public static final int DELAY_DAYS = 3;

    public static final long DAY_MILL = 24 * 60 * 60 * 1000;

    public static final int IM_EXAMINE_QUERY_MAX_SIZE = 300;

    public static final int SEQ_LEN_5 = 5;

    public static final int DEF_SEQ_LEN = SEQ_LEN_5;


    public static final int SEQ_LEN_8 = 8;


    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");


    /**
     * i18n
     */
    public static final String I18N_BASE_NAME = "i18n/messages";


    public static final Pair<Integer, Integer> RANDOM_MIN_MAX = Pair.of(8, 16);


    public static final String MARKET_GOODS_BTN_CONTENT = "抢购";


    public static final LocalTime ZERO_TIME = LocalTime.of(0, 0, 0);

    public static final LocalTime END_TIME = LocalTime.of(23, 59, 59);

    public static final LocalTime EXPIRE_END_TIME = LocalTime.of(23, 55, 0);


    public static final String PAY_SUCCESS = "success";


    public static final String PAY_FAILED = "failed";


}
