package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import test.oauth2.common.Constants;

import java.util.Map;

@Slf4j
public class CustomJwtTokenStore extends JwtTokenStore {

    private CustomJwtTokenConverter customJwtTokenConverter;
    private CustomBearerTokenExtractor customBearerTokenExtractor;

    public CustomJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
        this.customJwtTokenConverter = (CustomJwtTokenConverter) jwtTokenEnhancer;
    }

    public void setTokenExtractor(CustomBearerTokenExtractor extractor){ this.customBearerTokenExtractor = extractor; }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return super.readAccessToken(tokenValue);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return super.readRefreshToken(tokenValue);
    }

    private OAuth2AccessToken convertAccessToken(String tokenValue) {

        Map<String, Object> tokenValueMap = customJwtTokenConverter.decode(tokenValue);
        String ipAddress;

        ipAddress = tokenValueMap.get(Constants.CLIENT_IP_ADDRESS).toString();
        log.info("ip address in the token: " + ipAddress);

        log.info("remote addr: " + (customBearerTokenExtractor.request == null ?
                                        null : customBearerTokenExtractor.request.getRemoteAddr()));
        log.info("local addr: " + (customBearerTokenExtractor.request == null ?
                                        null : customBearerTokenExtractor.request.getLocalAddr()));

        if(checkIpAddress(ipAddress)) {
            return customJwtTokenConverter.extractAccessToken(tokenValue, tokenValueMap);
        }

        return null;
    }

    private boolean checkIpAddress(String ipAddress) {
        IpAddressMatcher matcher = new IpAddressMatcher(ipAddress);
        return matcher.matches(customBearerTokenExtractor.request);
    }

}
