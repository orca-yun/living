package ag.orca.living.util;

import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DateUtil {

    public static <T> List<T> dateRangeFunc(LocalDate start,
                                            LocalDate stop,
                                            Pair<ChronoUnit, Long> x,
                                            Function<LocalDate, T> func) {
        if (start == null || stop == null || start.isAfter(stop)) {
            return Collections.emptyList();
        } else {
            ArrayList<T> list = new ArrayList<>();
            LocalDate current = start;
            while (!current.isAfter(stop)) {
                T result = func.apply(current);
                list.add(result);
                switch (x.getLeft()) {
                    case MONTHS -> current = current.plusMonths(x.getRight());
                    case WEEKS -> current = current.plusWeeks(x.getRight());
                    case DAYS -> current = current.plusDays(x.getRight());
                    default -> throw new RuntimeException("不支持");
                }
            }
            return list;
        }
    }

    public static <T> List<T> dateTimeRangeFunc(LocalDateTime start,
                                                LocalDateTime stop,
                                                Pair<ChronoUnit, Long> x,
                                                Function<LocalDateTime, T> func) {
        if (start == null || stop == null || start.isAfter(stop)) {
            return Collections.emptyList();
        } else {
            ArrayList<T> list = new ArrayList<>();
            LocalDateTime current = start;
            while (!current.isAfter(stop)) {
                T result = func.apply(current);
                list.add(result);
                switch (x.getLeft()) {
                    case YEARS -> current = current.plusYears(x.getRight());
                    case MONTHS -> current = current.plusMonths(x.getRight());
                    case WEEKS -> current = current.plusWeeks(x.getRight());
                    case DAYS -> current = current.plusDays(x.getRight());
                    case HOURS -> current = current.plusHours(x.getRight());
                    case MINUTES -> current = current.plusMinutes(x.getRight());
                    case SECONDS -> current = current.plusSeconds(x.getRight());
                    default -> throw new RuntimeException("不支持");
                }
            }
            return list;
        }
    }


    public static LocalDateTime dateTimeOfTimestamp(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
