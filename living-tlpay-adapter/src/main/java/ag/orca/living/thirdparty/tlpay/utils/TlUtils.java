package ag.orca.living.thirdparty.tlpay.utils;

import ag.orca.living.thirdparty.tlpay.enums.SignTypeEnum;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * <p>
 * 通联工具类
 * </p>
 */
public final class TlUtils {
    private TlUtils() {
    }

    public static String randomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成数字签证
     *
     * @param param      参数
     * @param privateKey 签名密钥
     * @param signType   签名方式
     * @return String
     */
    public static String createSign(JSONObject param,
                                    String privateKey,
                                    String signType) throws Exception {
        SignTypeEnum signTypeEnum = SignTypeEnum.fromCode(signType);
        JSONObject j = new JSONObject(param);
        //签名前需要移除sign
        j.remove("sign");
        if (SignTypeEnum.MD5 == signTypeEnum) {
            //md5需要把md5的key加入到排序
            j.put("key", privateKey);
        }
        String paramStr = TlUtils.paramToStr(j);
        //开始加签
        if (SignTypeEnum.MD5 == signTypeEnum) {
            return MD5(paramStr);
        }
        if (SignTypeEnum.SM2 == signTypeEnum) {
            return TlUtils.SM2(privateKey, j.getString("appid"), paramStr);
        }
        return TlUtils.RSA(privateKey, paramStr);
    }


    /**
     * 校验数字签证
     *
     * @param param    参数
     * @param pubKey   签名公钥
     * @param signType 签名方式
     * @return boolean
     */
    public static boolean validSign(JSONObject param,
                                    String pubKey,
                                    String signType) throws Exception {
        SignTypeEnum signTypeEnum = SignTypeEnum.fromCode(signType);
        //参数缺失或者签名缺失返回false
        if (CollectionUtils.isEmpty(param) || !param.containsKey("sign")) {
            return false;
        }
        JSONObject j = new JSONObject(param);
        //得到移除sign
        String sign = param.remove("sign").toString();
        if (SignTypeEnum.MD5 == signTypeEnum) {
            //md5需要把md5的key加入到排序
            j.put("key", pubKey);
        }
        String content = TlUtils.paramToStr(j);

        //开始加签
        if (SignTypeEnum.MD5 == signTypeEnum) {
            return validMD5(sign, content);
        }
        if (SignTypeEnum.SM2 == signTypeEnum) {
            return validSM2(sign, content, pubKey);
        }
        return validRSA(sign, content, pubKey, StandardCharsets.UTF_8);
    }

    public static boolean validSign(TreeMap<String, String> param,
                                    String pubKey,
                                    String signType) throws Exception {
        SignTypeEnum signTypeEnum = SignTypeEnum.fromCode(signType);
        if (param != null && !param.isEmpty()) {
            if (!param.containsKey("sign")) {
                return false;
            }
            String sign = param.remove("sign");
            // 如果是md5则需要把md5的key加入到排序
            if (SignTypeEnum.MD5 == signTypeEnum) {
                param.put("key", pubKey);
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    sb.append(entry.getKey()).append("=")
                            .append(entry.getValue()).append("&");
                }
            }
            if (!sb.isEmpty()) {
                sb.deleteCharAt(sb.length() - 1);
            }
            String content = sb.toString();
            if (SignTypeEnum.MD5 == signTypeEnum) {
                return validMD5(sign, content);
            }
            if (SignTypeEnum.SM2 == signTypeEnum) {
                return validSM2(sign, content, pubKey);
            }
            return validRSA(sign, content, pubKey, StandardCharsets.UTF_8);
        }
        return false;
    }

    private static boolean validRSA(String sign,
                                    String content,
                                    String pubKey,
                                    Charset charset) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(pubKey.getBytes())));
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey);
        if (charset == null) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }
        return signature.verify(Base64.decodeBase64(sign.getBytes()));

    }


    /*具体加签内容来自Demo*/
    private static String MD5(String paramStr) {
        byte[] b = paramStr.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuilder sb = new StringBuilder(32);
            for (byte value : hash) {
                int v = value & 0xFF;
                if (v < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toString(v, 16).toLowerCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }

    private static String SM2(String privateKey, String appId, String paramStr) throws Exception {
        PrivateKey priKey = SmUtil.privKeySM2FromBase64Str(privateKey);
        return SmUtil.signSM3SM2RetBase64(priKey, appId, paramStr.getBytes(StandardCharsets.UTF_8));
    }

    private static String RSA(String privateKey, String paramStr) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes())));

        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(priKey);
        signature.update(paramStr.getBytes(StandardCharsets.UTF_8));
        byte[] signed = signature.sign();
        return new String(Base64.encodeBase64(signed));
    }

    /*具体签名校验来自Demo*/
    private static boolean validMD5(String sign, String paramStr) {
        return sign.equalsIgnoreCase(MD5(paramStr));
    }

    private static boolean validSM2(String sign, String paramStr, String pubKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(pubKey)));

        SM2ParameterSpec parameterSpec = new SM2ParameterSpec("Allinpay".getBytes());
        Signature verifier = Signature.getInstance("SM3withSM2", "BC");
        verifier.setParameter(parameterSpec);
        verifier.initVerify(publicKey);
        verifier.update(paramStr.getBytes(StandardCharsets.UTF_8));


        byte[] data = Base64.decodeBase64(sign);
        if (data.length != 32 * 2) throw new RuntimeException("err data. ");
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(data, 0, 32));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(data, 32, 32 * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        return verifier.verify(new DERSequence(v).getEncoded("DER"));
    }


    /**
     * 参数转URL参数格式的字符串
     *
     * @param param 参数
     * @return String a1=1&a2=2&b1=3&c=4
     */
    private static String paramToStr(JSONObject param) {
        StringBuilder sb = new StringBuilder();
        //所有参与签名的字段，按字段名的ASCII码从小到大排序
        TreeSet<String> sortSet = new TreeSet<>(param.keySet());
        //使用URL的键值对的格式(即key1=value1&key2=value2)拼接成字符串
        for (String key : sortSet) {
            sb.append(key).append("=").append(param.getString(key)).append("&");
        }

        //移除末尾多余的&
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
