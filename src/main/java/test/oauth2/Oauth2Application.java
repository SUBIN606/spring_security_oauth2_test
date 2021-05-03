package test.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import test.oauth2.common.AES256;
import test.oauth2.config.JwtProperties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@EnableConfigurationProperties({JwtProperties.class})
@SpringBootApplication
public class Oauth2Application {

	public static void main(String[] args) {

		SpringApplication.run(Oauth2Application.class, args);

	}

}
