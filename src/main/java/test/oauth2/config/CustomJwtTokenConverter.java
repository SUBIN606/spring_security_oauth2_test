package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import test.oauth2.common.AES256;
import test.oauth2.common.Constants;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    @Value("${jwt-encrypt-key}")
    private String JWT_ENCRYPT_KEY;

    @Value("${jwt-encrypt-iv}")
    private String JWT_ENCRYPT_IV;

    private final ClientDetailsService clientDetailsService;

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        String ipAddress = null;
        String pushUri = null;

        String clientId = authentication.getOAuth2Request().getClientId();
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        Map<String, Object> additionalInformation = clientDetails.getAdditionalInformation();
        log.info("additionalInformation = " +  additionalInformation);

        ipAddress = additionalInformation.get(Constants.CLIENT_IP_ADDRESS).toString();
        pushUri = additionalInformation.get(Constants.CLIENT_PUSH_URI).toString();

        claims.put(Constants.CLIENT_IP_ADDRESS, ipAddress);
        claims.put(Constants.CLIENT_PUSH_URI, pushUri);

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(claims);

        return super.enhance(customAccessToken, authentication);
    }

    /* JWT AES-256으로 암호화 */
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        try {
            return AES256.encrypt(super.encode(accessToken, authentication), JWT_ENCRYPT_KEY);
        } catch (Exception e) {
            log.error("error: " , e);
            return null;
        }
    }

    /* AES-256으로 암호화된 JWT 복호화 */
    protected Map<String, Object> decode(String token) {
        try {
            if(token.contains(".")){
                return super.decode(token);
            }else {
                String decoded = AES256.decrypt(token, JWT_ENCRYPT_KEY, JWT_ENCRYPT_IV);
                return super.decode(decoded);
            }
        } catch (Exception e) {
            log.error("error: " , e);
            return null;
        }
    }

}
