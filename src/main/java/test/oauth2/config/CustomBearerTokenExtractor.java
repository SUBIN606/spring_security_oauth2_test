package test.oauth2.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomBearerTokenExtractor extends BearerTokenExtractor {

    public HttpServletRequest request;

    @Override
    public Authentication extract(HttpServletRequest request) {
        this.request = request;
        return super.extract(request);
    }

    @Override
    protected String extractToken(HttpServletRequest request) {
        this.request = request;
        return super.extractToken(request);
    }

    @Override
    protected String extractHeaderToken(HttpServletRequest request) {
        this.request = request;
        return super.extractHeaderToken(request);
    }
}
