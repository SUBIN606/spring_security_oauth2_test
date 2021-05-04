package test.oauth2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomOAuth2RequestFactory extends DefaultOAuth2RequestFactory {

    public CustomOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    @Override
    public OAuth2Request createOAuth2Request(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> requestParams = tokenRequest.getRequestParameters();
        HashMap<String, String> modifiable = new HashMap<>(requestParams);

        modifiable.remove("password");
        modifiable.remove("client_secret");

        modifiable.put("grant_type", "password");

//        String redirectUri = null;
//        try {
//            redirectUri = client.getRegisteredRedirectUri().toArray()[0].toString();
//        } catch (Exception e) {
//            log.error("error = " , e);
//        }

        return new OAuth2Request(modifiable, client.getClientId(),
                                client.getAuthorities(), true,
                                tokenRequest.getScope(), client.getResourceIds(),
                                null, null, null);
    }
}
