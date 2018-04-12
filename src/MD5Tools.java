



import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
public class MD5Tools {

    /**
     * 随机获取密钥(公钥和私钥), 客户端公钥加密，服务器私钥解密
     *
     * @return 结果密钥对
     * @throws Exception 异常
     */
    public static Map<String, Object> getKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = getKeyPairGenerator();
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    /**
     * 获取公钥/私钥
     *
     * @param keyMap      密钥对
     * @param isPublicKey true：获取公钥，false：获取私钥
     * @return 获取密钥字符串
     */
    public static String getKey(Map<String, Object> keyMap, boolean isPublicKey) {
        Key key = (Key) keyMap.get(isPublicKey ? "RSAPublicKey" : "RSAPrivateKey");
        return new String(Base64.encode(key.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 获取数字签名
     *
     * @param data       二进制位
     * @param privateKey 私钥(BASE64编码)
     * @return 数字签名结果字符串
     * @throws Exception 异常
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey.getBytes(), Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getKeyFactory();
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64.encode(signature.sign(), Base64.DEFAULT));
    }

    /**
     * 数字签名校验
     *
     * @param data      二进位组
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名字符串
     * @return true：校验成功，false：校验失败
     * @throws Exception 异常
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey.getBytes(), Base64.DEFAULT);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getKeyFactory();
        PublicKey publicK = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decode(sign.getBytes(), Base64.DEFAULT));
    }

    /**
     * 获取 KeyFactory
     *
     * @throws NoSuchAlgorithmException 异常
     */
    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException,
            NoSuchProviderException {
        KeyFactory keyFactory;
//        if (Build.VERSION.SDK_INT >= 16) {
//            keyFactory = KeyFactory.getInstance("RSA", "BC");
//        } else {
//        	keyFactory = KeyFactory.getInstance("RSA");
//        }
        keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory;
    }

    /**
     * 获取 KeyFactory
     *
     * @throws NoSuchAlgorithmException 异常
     */
    private static KeyPairGenerator getKeyPairGenerator() throws NoSuchProviderException,
            NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen;
//        if (Build.VERSION.SDK_INT >= 16) {
//            keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
//        } else {
//        	keyPairGen = KeyPairGenerator.getInstance("RSA");
//        }
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        return keyPairGen;
    }


    /**
     * Rsa公钥加密类型
     */
    public static final int RSA_PUBLIC_ENCRYPT = 0;

    /**
     * Rsa公钥解密类型
     */
    public static final int RSA_PUBLIC_DECRYPT = 1;

    /**
     * Rsa私钥加密类型
     */
    public static final int RSA_PRIVATE_ENCRYPT = 2;

    /**
     * Rsa私钥解密类型
     */
    public static final int RSA_PRIVATE_DECRYPT = 3;

//    @IntDef({RSA_PUBLIC_ENCRYPT, RSA_PUBLIC_DECRYPT, RSA_PRIVATE_ENCRYPT, RSA_PRIVATE_DECRYPT})
    @interface RSAType {}

    /**
     * Rsa加密/解密（一般情况下，公钥加密私钥解密）
     *
     * @param data   源数据
     * @param string 密钥(BASE64编码)
     * @param type   操作类型：{@link #RSA_PUBLIC_ENCRYPT}，{@link #RSA_PUBLIC_DECRYPT
     *               }，{@link #RSA_PRIVATE_ENCRYPT}，{@link #RSA_PRIVATE_DECRYPT}
     * @return 加密/解密结果字符串
     * @throws Exception 异常
     */
    public static byte[] rsa(byte[] data, String string, @RSAType int type) throws Exception {
        byte[] keyBytes = Base64.decode(string, Base64.DEFAULT);

        Key key;
        KeyFactory keyFactory = getKeyFactory();
        if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PUBLIC_DECRYPT) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);
        }

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        // 对数据分段加密
        if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PRIVATE_ENCRYPT) {
            cipher.init(Cipher.ENCRYPT_MODE, key);

            while (inputLen - offSet > 0) {
                cache = cipher.doFinal(data, offSet, inputLen - offSet > 117 ? 117 : inputLen -
                        offSet);
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 117;
            }
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);

            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(data, offSet, 128);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
        }
        byte[] result = out.toByteArray();
        out.close();
        return result;
    }
    

    public static void main(String[] args) throws Exception {
    	// des 字符串加密解密测试
    	byte[] data = "123456".getBytes();

    	// 公钥加密私钥解密
    	byte[] rsaPublic =
    	        MD5Tools.rsa(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqZncxY7qT1UUZU"
    	        		+ "PUvJOV4PyxiKH3whvtv18kW1AIJUYUzIxEERRvF85k88uQ+677kiNIMGmtAGYr/IT4B"
    	        		+ "FUPnFTCaovtRSNhEM/QjU1jK+XmioMLjbB5npJMOLQp/fCJ2NhiikcVqKaSmZ8lxe0m/Yzf"
    	        		+ "M6eWPohpvmDcB/AIZGQIDAQAB", MD5Tools.RSA_PUBLIC_ENCRYPT);
    	System.out.println("rsa公钥加密： " + rsaPublic);
    	
    	System.out.println("rsa私钥解密： " + new String(
    			MD5Tools.rsa(rsaPublic, "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGBAKpmdzFjupPVRRlQ"
    					+ "9S8k5Xg/LGIoffCG+2/XyRbUAglRhTMjEQRFG8XzmTzy5D7rvuSI0gwaa0AZiv8h"
    					+ "PgEVQ+cVMJqi+1FI2EQz9CNTWMr5eaKgwuNsHmekkw4tCn98InY2GKKRxWoppKZn"
    					+ "yXF7Sb9jN8zp5Y+iGm+YNwH8AhkZAgMBAAECfy9CO8x23Egj3gYnbdilYv72hliy"
    					+ "4jrTNOjP7/Vk1yu+cDK+hvmoMQu1g8pjkungeBDp4Vd5/ETK0MHxdkDWpP39//aN"
    					+ "7QwtaDaNTAL0nwTzWPqXsM1uZS8Seq/bwDfeBEUrLNf6qjuf+N7Z2uLG9SmJJgoL"
    					+ "WKdZsCWR06pNnv0CQQDV5TIY39PHEODVDIM7CFrSRxCC+fpfBSfQW3SZcsm2BmS/"
    					+ "hGHO8XovhXFVhx7vqydDHLn0zyT8MHGgdEh8MQxLAkEAy/FtwJeEWHpDEVj5U1Qi"
    					+ "X5CSVQ+ZSa531W6Nis2ErAXwWCDUCRtJEp85UItiS0hHBb7cLWM5Q1ReZAZifsjJ"
    					+ "qwJAJDvlVq1kFM7ZJ34tJOzvZbvAIk/yh/I5omQ7xlrZrMo8eu0UHJhvmGlEn7Z6"
    					+ "GSOnTQkqvzvwCgCy3vmT5eXxLQJAXnoBde67CKHa3/ZvygJ3yf2XTIBlapTAPsdw"
    					+ "GpUKmQZHSQwsLevN9ip8yqtHZcid3jOEnCITLPWwpHWwuzsY8QJBANPQ1o1mzI4K"
    					+ "FeodwKYoipYN3zxbvB0PviWJ7/sVf1QCaqj/tnF3Z1qdOyoYhqN3PlZxyij719D0"
    					+ "fRaawwpxyZY=", MD5Tools.RSA_PRIVATE_DECRYPT)));

    	
    	
    	
    }



}
