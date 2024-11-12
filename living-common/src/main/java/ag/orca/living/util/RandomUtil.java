package ag.orca.living.util;

public class RandomUtil {


    public static final int MIN_VALUE = 100000;

    public static final int MAX_VALUE = 999999;

    public static long randomId(int min, int max) {
        return (long) (Math.random() * (max - min + 1)) + min;
    }

    public static long randomId() {
        return (long) (Math.random() * (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;
    }
}
