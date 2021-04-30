package test.oauth2.common;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES256 {

    private static final String SECRET_KEY = "fUjXn2r5u8x/A?D(G+KbPdSgVkYp3s6v"; // 암/복호화에 사용될 Key (256bit)
    private static final String IV = "ShVmYq3t6w9y$B&E";                         // 블록 암호화에 처음 사용되는 값 (128bit)
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";         // Block Cipher 모드 설정

    /* 암호화 */
    public static String encrypt(String str) throws NoSuchPaddingException, NoSuchAlgorithmException,
                                                    InvalidAlgorithmParameterException, InvalidKeyException,
                                                    IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] result = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(result);
    }

    /* 복호화 */
    public static String decrypt(String str) throws NoSuchPaddingException, NoSuchAlgorithmException,
                                                    InvalidAlgorithmParameterException, InvalidKeyException,
                                                    IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decoded = Base64.getDecoder().decode(str);
        byte[] result = cipher.doFinal(decoded);

        return new String(result, "UTF-8");
    }
}
