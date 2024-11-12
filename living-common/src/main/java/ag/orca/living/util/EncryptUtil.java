package ag.orca.living.util;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.Security;

public class EncryptUtil {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * DESede需要24字节密钥，8字节IV
     */
    private static final byte[] DES_KEY_BYTES = "&4MDF44UU^@YkGYq78UY*YYm".getBytes(StandardCharsets.UTF_8);

    /**
     * 8字节IV
     */
    private static final byte[] DES_IV_BYTES = "5xd6PACG".getBytes(StandardCharsets.UTF_8);


    public static String desEncrypt(String plain) {
        try {
            byte[] plainBytes = plain.getBytes(StandardCharsets.UTF_8);
            CBCBlockCipher engine = new CBCBlockCipher(new DESedeEngine());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine, new PKCS7Padding());
            ParametersWithIV params = new ParametersWithIV(new KeyParameter(DES_KEY_BYTES), DES_IV_BYTES);
            cipher.init(true, params);
            byte[] cipherBytes = new byte[cipher.getOutputSize(plainBytes.length)];
            int len = cipher.processBytes(plainBytes, 0, plainBytes.length, cipherBytes, 0);
            cipher.doFinal(cipherBytes, len);
            return Hex.toHexString(cipherBytes);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String desDecrypt(String cipherText) {
        try {
            byte[] cipherBytes = Hex.decode(cipherText);
            CBCBlockCipher engine = new CBCBlockCipher(new DESedeEngine());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine, new PKCS7Padding());
            ParametersWithIV params = new ParametersWithIV(new KeyParameter(DES_KEY_BYTES), DES_IV_BYTES);
            cipher.init(false, params);
            byte[] decryptedBytes = new byte[cipher.getOutputSize(cipherBytes.length)];
            int len = cipher.processBytes(cipherBytes, 0, cipherBytes.length, decryptedBytes, 0);
            len += cipher.doFinal(decryptedBytes, len);
            return new String(decryptedBytes, 0, len);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String sha256Encrypt(String password) {
        if (StringUtils.isNotBlank(password)) {
            SHA256Digest digest = new SHA256Digest();
            byte[] bytes = password.getBytes();
            digest.update(bytes, 0, bytes.length);
            byte[] finalBytes = new byte[digest.getDigestSize()];
            digest.doFinal(finalBytes, 0);
            return Hex.toHexString(finalBytes);
        }
        return "";
    }


}
