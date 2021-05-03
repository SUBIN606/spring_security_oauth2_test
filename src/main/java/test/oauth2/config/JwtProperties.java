package test.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {

    private final String JWT_ENCRYPT_KEY;
    private final String JWT_ENCRYPT_IV;
    private final String JWT_SIGNING_KEY;

    public JwtProperties(String encryptKey, String encryptIv, String signingKey) {
        this.JWT_ENCRYPT_KEY = encryptKey;
        this.JWT_ENCRYPT_IV = encryptIv;
        this.JWT_SIGNING_KEY = signingKey;
    }

    public String getJWT_ENCRYPT_KEY() {
        return JWT_ENCRYPT_KEY;
    }

    public String getJWT_ENCRYPT_IV() {
        return JWT_ENCRYPT_IV;
    }

    public String getJWT_SIGNING_KEY() {
        return JWT_SIGNING_KEY;
    }
}

