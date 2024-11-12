package ag.orca.living.core;


import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz".toUpperCase() + "abcdefghijklmnopqrstuvwxyz0123456789";


    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static int randomInt(int limitExclude) {
        return getRandom().nextInt(limitExclude);
    }

    public static String randomString(String baseString, int length) {
        if (!StringUtils.hasText(baseString)) {
            return "";
        } else {
            if (length < 1) {
                length = 1;
            }

            StringBuilder sb = new StringBuilder(length);
            int baseLength = baseString.length();

            for (int i = 0; i < length; ++i) {
                int number = randomInt(baseLength);
                sb.append(baseString.charAt(number));
            }

            return sb.toString();
        }
    }
}
