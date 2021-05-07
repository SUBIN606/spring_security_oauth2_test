package test.oauth2.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomPasswordEncoder implements PasswordEncoder {

    public static char[] key = {0x84, 0x8c, 0x92, 0x8a, 0x94, 0x84, 0x8d, 0x93, 0x8d, 0x87, 0x97};

    public static String getEncrypt(String source, byte[] salt)throws Exception {

        String result = "";

        byte[] a = source.getBytes();
        byte[] bytes = new byte[a.length + salt.length];

        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(salt, 0, bytes, a.length, salt.length);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(source.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            result = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] toBytes_256(char[] data) {
        byte[] toRet = new byte[data.length];
        for (int i = 0; i < toRet.length; i++) {
            toRet[i] = (byte) data[i];
        }
        return toRet;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return getEncrypt(rawPassword.toString(),toBytes_256(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            if (encodedPassword.equals(getEncrypt(rawPassword.toString(),toBytes_256(key)))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
