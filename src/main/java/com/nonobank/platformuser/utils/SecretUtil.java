package com.nonobank.platformuser.utils;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.*;
import java.util.Arrays;


/**
 * Created by tangrubei on 2018/2/26.
 */
public class SecretUtil {


    private final static String passCode = "IlovaMaizi";

    private final static String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private final static int KEY_SIZE = 192;

    private static final int ITERATIONS = 1;

    private static final int IV_LENGTH = 16;




    /**
     * 去掉加密算法的位数限制
     */
    static {
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE);
        } catch (Exception ex) {
        }
    }


    /**
     * Thanks go to Ola Bini for releasing this source on his blog. The source was obtained from
     * <a href="http://olabini.com/blog/tag/evp_bytestokey/">here</a>
     * <p><a href="https://github.com/nodejs/node/blob/master/deps/openssl/openssl/crypto/evp/evp_key.c">原node的C代码</a></p>
     * <p><a href="https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c">原openssl的C代码</a></p>
     */
    private static byte[][] EVP_BytesToKey(
            int key_len, int iv_len, MessageDigest md, byte[] salt, byte[] data, int count) {
        byte[][] both = new byte[2][];
        byte[] key = new byte[key_len];
        int key_ix = 0;
        byte[] iv = new byte[iv_len];
        int iv_ix = 0;
        both[0] = key;
        both[1] = iv;
        byte[] md_buf = null;
        int nkey = key_len;
        int niv = iv_len;
        int i = 0;
        if (data == null) {
            return both;
        }
        int addmd = 0;
        for (; ; ) {
            md.reset();
            if (addmd++ > 0) {
                md.update(md_buf);
            }
            md.update(data);
            if (null != salt) {
                md.update(salt, 0, 8);
            }
            md_buf = md.digest();
            for (i = 1; i < count; i++) {
                md.reset();
                md.update(md_buf);
                md_buf = md.digest();
            }
            i = 0;
            if (nkey > 0) {
                for (; ; ) {
                    if (nkey == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    key[key_ix++] = md_buf[i];
                    nkey--;
                    i++;
                }
            }
            if (niv > 0 && i != md_buf.length) {
                for (; ; ) {
                    if (niv == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    iv[iv_ix++] = md_buf[i];
                    niv--;
                    i++;
                }
            }
            if (nkey == 0 && niv == 0) {
                break;
            }
        }
        for (i = 0; i < md_buf.length; i++) {
            md_buf[i] = 0;
        }
        return both;
    }


    private static Cipher createCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        MessageDigest md5 = MessageDigest.getInstance("md5");

        byte[][] evp_BytesToKey = EVP_BytesToKey(KEY_SIZE / Byte.SIZE, IV_LENGTH, md5, null, passCode.getBytes(), ITERATIONS);
        byte[] KEY_EVP = evp_BytesToKey[0];
        byte[] IV_EVP = evp_BytesToKey[1];
        IvParameterSpec ivp = new IvParameterSpec(Arrays.copyOf(IV_EVP, IV_EVP.length));
        SecretKeySpec secretKey = new SecretKeySpec(Arrays.copyOf(KEY_EVP, KEY_EVP.length), DEFAULT_CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        cipher.init(mode, secretKey, ivp);
        return cipher;
    }

    public static String encryContent2ByteHexStr(String content) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher encryptCipher = createCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedByte = encryptCipher.doFinal(content.getBytes());
        return org.bouncycastle.util.encoders.Hex.toHexString(encryptedByte);
    }

    public static String decryContent(String encryptedByteHexStr) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] encryByte = org.bouncycastle.util.encoders.Hex.decode(encryptedByteHexStr);
        Cipher decryptCipher = createCipher(Cipher.DECRYPT_MODE);
        byte[] decryByte = decryptCipher.doFinal(encryByte);
        return new String(decryByte);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String enstr = encryContent2ByteHexStr("Pass2018@");
        System.out.println(enstr);
        String decryContent = decryContent(enstr);
        System.out.println(decryContent);

        /*
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String node_secret = "IlovaMaizi"; // 对应node的第2个参数 crypto.createDecipher('aes192', secret)

        MessageDigest md5 = MessageDigest.getInstance("md5");

        // 参数说明 http://nodejs.cn/doc/nodejs_4/all.html#all_crypto_createcipher_algorithm_password
        byte[][] evp_BytesToKey = EVP_BytesToKey(192/8, 16, md5, null, node_secret.getBytes(), 1);
        byte[] KEY_EVP = evp_BytesToKey[0];
        byte[] IV_EVP = evp_BytesToKey[1];


        IvParameterSpec ivp = new IvParameterSpec(Arrays.copyOf(IV_EVP, IV_EVP.length));
        SecretKeySpec secretKey = new SecretKeySpec(Arrays.copyOf(KEY_EVP, KEY_EVP.length), "AES/CBC/PKCS5Padding");

        String content = "Pass2018@";

        //加密
        Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivp);
        byte[] encryptedByte = encryptCipher.doFinal(content.getBytes());
        String encryptedByteHexStr = org.bouncycastle.util.encoders.Hex.toHexString(encryptedByte);
        System.out.println(encryptedByteHexStr);

        // 解密
        byte[] byte0 = org.bouncycastle.util.encoders.Hex.decode(encryptedByteHexStr);
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivp);
        byte[] byte1 = decryptCipher.doFinal(byte0);
        System.out.println(new String(byte1));
        */

    }


}
