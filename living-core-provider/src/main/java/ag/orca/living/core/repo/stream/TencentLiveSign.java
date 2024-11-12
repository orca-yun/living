package ag.orca.living.core.repo.stream;

import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TencentLiveSign {
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * KEY+ streamName + txTime
     * 生成安全的 鉴权KEY
     * buildAuthKey("txrtmp", "11212122", 1469762325L)
     *
     * @param key
     * @param streamName
     * @param txTime
     * @return
     */
    @SneakyThrows
    public static Pair<String, String> buildSecret(String key, String streamName, long txTime) {
        String hexTxTime = Long.toHexString(txTime).toUpperCase();
        String input = key + streamName + hexTxTime;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
        return Pair.of("txSecret=" + txSecret, "txTime=" + hexTxTime);
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

}
