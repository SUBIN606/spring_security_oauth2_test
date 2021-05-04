package test.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")    // application.yml 파일에서 jwt로 시작되는 값들을 읽는다
@ConstructorBinding                         // private final 필드로 사용할 수 있도록 해줌 (setter 사용하지 않기 위해)
public class JwtProperties {

    private final String JWT_ENCRYPT_KEY;   // AES-256 Key (256bit)
    private final String JWT_ENCRYPT_IV;    // AES-256 블록 암호화에 사용될 기본 값 (128bit)
    private final String JWT_SIGNING_KEY;   // JWT 암호화 Key

    /* 생성자 주입 */
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

