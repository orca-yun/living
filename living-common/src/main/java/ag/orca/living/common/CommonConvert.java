package ag.orca.living.common;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommonConvert {

    /**
     * stream list map
     */
    public static <T, R> List<R> map(List<T> list, Predicate<? super T> filter, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().filter(Objects::nonNull).filter(filter).map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }


    /**
     * stream list map
     */
    public static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().filter(Objects::nonNull).map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T, R> List<R> distinctMap(List<T> list, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().filter(Objects::nonNull).map(mapper).distinct().collect(Collectors.toList());
    }

    public static <T> List<T> filter(List<T> list, Predicate<? super T> filter) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().filter(Objects::nonNull).filter(filter).collect(Collectors.toList());
    }


}
